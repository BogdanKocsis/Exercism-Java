import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ledger {

    // --- Public API (unchanged) ------------------------------------------------

    public LedgerEntry createLedgerEntry(String isoDate, String description, int changeInCents) {
        return new LedgerEntry(LocalDate.parse(isoDate), description, changeInCents);
    }

    public String format(String currency, String locale, LedgerEntry[] entries) {
        Config cfg = Config.of(currency, locale);

        StringBuilder out = new StringBuilder(cfg.header);

        if (entries.length == 0) {
            return out.toString();
        }

        // stable, explicit ordering: negatives first (by date asc), then positives (by date asc)
        List<LedgerEntry> all = splitAndSort(entries);

        for (LedgerEntry e : all) {
            String date = e.date().format(DateTimeFormatter.ofPattern(cfg.datePattern));
            String desc = truncate(e.description());
            String amount = formatAmount(e.changeInCents(), cfg);

            out.append('\n')
                    .append(String.format("%s | %-25s | %13s", date, desc, amount));
        }

        return out.toString();
    }

    // --- Behavior-preserving helpers ------------------------------------------

    private static List<LedgerEntry> splitAndSort(LedgerEntry[] entries) {
        List<LedgerEntry> neg = new ArrayList<>();
        List<LedgerEntry> pos = new ArrayList<>();
        for (LedgerEntry e : entries) {
            if (e.changeInCents() < 0) neg.add(e);
            else pos.add(e);
        }
        Comparator<LedgerEntry> byDate = Comparator.comparing(LedgerEntry::date);
        neg.sort(byDate);
        pos.sort(byDate);
        List<LedgerEntry> all = new ArrayList<>(neg.size() + pos.size());
        all.addAll(neg);
        all.addAll(pos);
        return all;
    }

    private static String truncate(String s) {
        if (s.length() <= 25) return s;
        // 22 chars + "..." = 25 (exactly like the original)
        return s.substring(0, 22) + "...";
    }

    private static String formatAmount(int cents, Config cfg) {
        boolean negative = cents < 0;
        long absCents = Math.abs((long) cents);

        long units = absCents / 100;
        int frac = (int) (absCents % 100);

        String integerPart = groupThousands(Long.toString(units), cfg.thousandsSep);
        String decimalPart = String.format("%02d", frac);

        // Base amount with symbol & separators, without sign decoration
        String base = cfg.locale.equals("nl-NL")
                ? cfg.currencySymbol + " " + integerPart + cfg.decimalSep + decimalPart
                : cfg.currencySymbol + integerPart + cfg.decimalSep + decimalPart;

        // Apply negative / spacing rules to match the old behavior exactly
        if (negative) {
            if (cfg.locale.equals("en-US")) {
                // en-US negatives are wrapped in parentheses, no extra trailing space
                return "(" + base + ")";
            } else {
                // nl-NL negatives: "€ -X,YY " (note the trailing space)
                String withoutSymbol = base.replace(cfg.currencySymbol, "").trim();
                return cfg.currencySymbol + " -" + withoutSymbol + " ";
            }
        } else {
            // positives
            if (cfg.locale.equals("nl-NL")) {
                // both leading and trailing spaces around the amount
                return " " + base + " ";
            } else {
                // en-US positive: one trailing space
                return base + " ";
            }
        }
    }

    private static String groupThousands(String digits, String sep) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = digits.length() - 1; i >= 0; i--) {
            sb.append(digits.charAt(i));
            count++;
            if (i > 0 && count % 3 == 0) sb.append(sep);
        }
        return sb.reverse().toString();
    }

    // --- Configuration ---------------------------------------------------------

    /**
     * @param locale       "en-US" or "nl-NL"
     * @param datePattern  "MM/dd/yyyy" or "dd/MM/yyyy"
     * @param decimalSep   "." or ","
     * @param thousandsSep "," or "."
     */
    private record Config(String currencySymbol, String locale, String datePattern, String decimalSep,
                          String thousandsSep, String header) {

        static Config of(String currency, String locale) {
                if (!currency.equals("USD") && !currency.equals("EUR"))
                    throw new IllegalArgumentException("Invalid currency");
                if (!locale.equals("en-US") && !locale.equals("nl-NL"))
                    throw new IllegalArgumentException("Invalid locale");

                String symbol = currency.equals("USD") ? "$" : "€";
                if (locale.equals("en-US")) {
                    return new Config(
                            symbol,
                            "en-US",
                            "MM/dd/yyyy",
                            ".",
                            ",",
                            "Date       | Description               | Change       "
                    );
                } else {
                    return new Config(
                            symbol,
                            "nl-NL",
                            "dd/MM/yyyy",
                            ",",
                            ".",
                            "Datum      | Omschrijving              | Verandering  "
                    );
                }
            }
        }

    // --- Data model ------------------------------------------------------------

    /**
     * @param changeInCents safer & precise vs double
     */
    public record LedgerEntry(LocalDate date, String description, int changeInCents) {
            public LedgerEntry(LocalDate date, String description, int changeInCents) {
                this.date = Objects.requireNonNull(date, "date");
                this.description = Objects.requireNonNull(description, "description");
                this.changeInCents = changeInCents;
            }
        }
}

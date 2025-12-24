import java.util.HashMap;
import java.util.Map;

class Tournament {
    public static Map<String, TeamStats> teamStats;

    Tournament() {
        teamStats = new HashMap<>();
    }

    static class TeamStats {
        int matchesPlayed;
        int wins;
        int draws;
        int losses;
        int points;

        TeamStats(int matchesPlayed, int wins, int draws, int losses, int points) {
            this.matchesPlayed = matchesPlayed;
            this.wins = wins;
            this.draws = draws;
            this.losses = losses;
            this.points = points;
        }
    }


    String printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team                           | MP |  W |  D |  L |  P\n");
        teamStats.entrySet().stream()
                .sorted((e1, e2) -> {
                    int pointCompare = Integer.compare(e2.getValue().points, e1.getValue().points);
                    if (pointCompare != 0) {
                        return pointCompare;
                    }
                    return e1.getKey().compareTo(e2.getKey());
                })
                .forEach(entry -> {
                    String team = entry.getKey();
                    TeamStats stats = entry.getValue();
                    sb.append(String.format("%-30s | %2d | %2d | %2d | %2d | %2d\n",
                            team, stats.matchesPlayed, stats.wins, stats.draws, stats.losses, stats.points));
                });
        return sb.toString();
    }

    void applyResults(String resultString) {
        if (resultString.contains("\n")) {
            String[] results = resultString.split("\n");
            for (String result : results) {
                applySingleResult(result);
            }
        } else {
            applySingleResult(resultString);
        }
    }

    private void applySingleResult(String resultString) {
        String[] parts = resultString.split(";");
        String teamA = parts[0];
        String teamB = parts[1];
        String result = parts[2];
        teamStats.putIfAbsent(teamA, new TeamStats(0, 0, 0, 0, 0));
        teamStats.putIfAbsent(teamB, new TeamStats(0, 0, 0, 0, 0));
        TeamStats statsA = teamStats.get(teamA);
        TeamStats statsB = teamStats.get(teamB);
        statsA.matchesPlayed++;
        statsB.matchesPlayed++;
        switch (result) {
            case "win" -> {
                statsA.wins++;
                statsA.points += 3;
                statsB.losses++;
            }
            case "loss" -> {
                statsB.wins++;
                statsB.points += 3;
                statsA.losses++;
            }
            case "draw" -> {
                statsA.draws++;
                statsB.draws++;
                statsA.points++;
                statsB.points++;
            }
        }
    }
}
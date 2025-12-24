import java.util.List;

record BinarySearch(List<Integer> list) {

    int indexOf(int item) throws ValueNotFoundException {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = list.get(mid);

            if (midValue == item) {
                return mid;
            } else if (midValue < item) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        throw new ValueNotFoundException("Value not in array");
    }
}

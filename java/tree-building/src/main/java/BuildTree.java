import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BuildTree {


    public final ArrayList<TreeNode> treeNodes = new ArrayList<>();

    public TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
        records.sort(Comparator.comparing(Record::getRecordId));
        validateRecords(records);

        for (Record record : records) {
            addTreeNode(record);
        }
        records.forEach(parent -> addChildren(parent, records));

        return treeNodes.isEmpty() ? null : treeNodes.getFirst();
    }

    private void addChildren(Record parent, ArrayList<Record> records) {
        TreeNode parentNode = null;

        for(Record child : getChildren(parent, records)){

            for (TreeNode node : treeNodes) {
                if (parent.getRecordId() == node.getNodeId()) {
                    parentNode = node;
                }
                if (child.getRecordId() == node.getNodeId() && node.getNodeId() != 0) {
                    assert parentNode != null;
                    parentNode.getChildren().add(node);
                }
            }
        }
    }


    public List<Record> getChildren(Record parent, ArrayList<Record> records) {
        return records.stream().filter(r -> r.getParentId() == parent.getRecordId()).collect(Collectors.toList());
    }

    private void addTreeNode(Record record) throws InvalidRecordsException {
        if ((record.getRecordId() == 0 && record.getParentId() != 0)
                || (record.getRecordId() < record.getParentId())
                || (record.getRecordId() == record.getParentId() && record.getRecordId() != 0)) {
            throw new InvalidRecordsException("Invalid Records");
        }
        treeNodes.add(new TreeNode(record.getRecordId()));
    }

    private void validateRecords(ArrayList<Record> records) throws InvalidRecordsException {
        if (!records.isEmpty()
                && ((records.getLast().getRecordId() != records.size() - 1)
                || (records.getFirst().getRecordId() != 0))
        ) {
            throw new InvalidRecordsException("Invalid Records");
        }
    }
}
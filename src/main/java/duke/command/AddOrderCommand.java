package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.CommandParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

/**
 * A command to add an <code>Order</code> object to an <code>OrderList</code> object.
 */
public class AddOrderCommand extends UndoableCommand {
    private Map<String, List<String>> params;
    private Order order;

    /**
     * Class constructor.
     *
     * @param params the parameters specifying details of the order.
     */
    public AddOrderCommand(Map<String, List<String>> params) {
        this.params = params;
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        order = new Order();
        CommandParser.modifyOrdrer(params, order);
        addOrder(order, bakingList);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        bakingList.getOrderList().remove(order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
    }

    private void addOrder(Order order, BakingList bakingList) {
        bakingList.getOrderList().add(0, order);
    }
}
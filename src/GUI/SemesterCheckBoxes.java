package GUI;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SemesterCheckBoxes {

    private ArrayList<CheckBox> checkBoxList = new ArrayList<CheckBox>();
    private HBox checkBoxSection = new HBox(10);

    // CLASS CURRENTLY NOT IN USE. IMPLEMENTED SLIDER INSTEAD.

    public HBox getCheckBoxSection() {
        return checkBoxSection;
    }

    public void setCheckBoxSection(HBox checkBoxSection) {
        this.checkBoxSection = checkBoxSection;
    }

    public ArrayList<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public static void setCheckBoxList(ArrayList<CheckBox> checkBoxList) {
        checkBoxList = checkBoxList;
    }

    public void initializeLabelsAndCheckBoxes() {

        Label label1 = new Label("1");
        Label label2 = new Label("2");
        Label label3 = new Label("3");
        Label label4 = new Label("4");
        Label label5 = new Label("5");

        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        CheckBox checkBox4 = new CheckBox();
        CheckBox checkBox5 = new CheckBox();

        checkBoxList.add(checkBox1);
        checkBoxList.add(checkBox2);
        checkBoxList.add(checkBox3);
        checkBoxList.add(checkBox4);
        checkBoxList.add(checkBox5);

        checkBoxSection.getChildren().addAll(label1, checkBox1, label2, checkBox2, label3, checkBox3, label4, checkBox4, label5, checkBox5);
    }
}

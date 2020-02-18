import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class JFileChooserTest {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFileChooser dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setDialogTitle("Виберіть потрібні файли та папки");
        dialog.setApproveButtonText("Відкрити");
        dialog.setMultiSelectionEnabled(true);
        dialog.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.toString().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "файли типу .txt";
            }
        });

        dialog.showOpenDialog(null);

        File[] files = dialog.getSelectedFiles();
        if (files == null)
            return;

        for (File file : files) {
            System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());
        }
    }
}

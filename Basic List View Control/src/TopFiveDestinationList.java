import java.awt.*;
import java.net.URL; // Added import for URL class to handle online images
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}


class TopDestinationListFrame extends JFrame {
    private DefaultListModel listModel;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel();

        // Updated: Adding the top 5 destinations with images from Wikimedia Commons
        addDestinationNameAndPicture("1. Paris, France - The City of Light, known for its culture and iconic landmarks.", 
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg/640px-Paris_-_Eiffelturm_und_Marsfeld2.jpg");

        addDestinationNameAndPicture("2. Bali, Indonesia - A tropical paradise famous for its beaches and temples.", 
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Mount_Lempuyang%2C_Volcanic_landform%2C_Bali%2C_Indonesia.jpg/640px-Mount_Lempuyang%2C_Volcanic_landform%2C_Bali%2C_Indonesia.jpg");

        addDestinationNameAndPicture("3. Tokyo, Japan - A blend of ultramodern and traditional culture.", 
            "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Minato_City%2C_Tokyo%2C_Japan.jpg/640px-Minato_City%2C_Tokyo%2C_Japan.jpg");

        addDestinationNameAndPicture("4. New York City, USA - The city that never sleeps, known for its skyline and diverse culture.", 
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Long_Island_City_New_York_May_2015_panorama_3.jpg/640px-Long_Island_City_New_York_May_2015_panorama_3.jpg");

        addDestinationNameAndPicture("5. Rome, Italy - The Eternal City, rich in history and ancient architecture.", 
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Colosseum_in_Rome%2C_Italy_-_April_2007.jpg/640px-Colosseum_in_Rome%2C_Italy_-_April_2007.jpg");

        // Custom label to display your name at the bottom of the window
        JLabel nameLabel = new JLabel("Created by: Kane Miller", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        // Custom color scheme: Dark gray background and white text for selected items, black and light gray for unselected
        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(10);
        list.setCellRenderer(renderer);

        // Adding components to the JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(nameLabel, BorderLayout.SOUTH);
    }

    // Updated: Method to add a destination's name, description, and image (from URL)
    private void addDestinationNameAndPicture(String text, String imageUrl) {
        try {
            // Resize the image to a smaller size (e.g., 200x150 pixels)
            ImageIcon originalIcon = new ImageIcon(new URL(imageUrl));
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);  // Use the scaled image
            
            TextAndIcon tai = new TextAndIcon(text, scaledIcon);
            listModel.addElement(tai);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class TextAndIcon {
    private String text;
    private Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}


class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private Border insideBorder;

    public TextAndIconListCellRenderer(int padding) {
        insideBorder = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean hasFocus) {
        TextAndIcon tai = (TextAndIcon) value;
        setText(tai.getText());
        setIcon(tai.getIcon());

        // Custom color scheme for selected and unselected items
        setFont(new Font("Arial", Font.PLAIN, 16));  // Updated font size for larger text
        if (isSelected) {
            setBackground(Color.DARK_GRAY);  // Dark gray background when selected
            setForeground(Color.WHITE);      // White text when selected
        } else {
            setBackground(Color.BLACK);      // Black background when unselected
            setForeground(Color.LIGHT_GRAY); // Light gray text when unselected
        }

        Border outsideBorder = hasFocus
                ? UIManager.getBorder("List.focusCellHighlightBorder")
                : NO_FOCUS_BORDER;

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    // Overriding empty methods for performance reasons
    public void validate() { }
    public void invalidate() { }
    public void repaint() { }
    public void revalidate() { }
    public void repaint(long tm, int x, int y, int width, int height) { }
    public void repaint(Rectangle r) { }
}

package ui;
import dao.ThongKeDAO;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ly Tinh Nhiem
 */

public class BieuDoDoanhThuJDialog extends ApplicationFrame {
    ThongKeDAO tkDAO = new ThongKeDAO();
    public BieuDoDoanhThuJDialog(boolean modal, String title, int year) {
        super(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> list = tkDAO.getDoanhThu(year);
        for (Object[] row : list) {
            dataset.addValue((Number)row[3], year,row[0].toString().replaceAll("Lập trình", ""));
        }

        JFreeChart chart = ChartFactory.createBarChart3D(
                "Biểu đồ doanh thu",
                "Năm",
                "VND",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        //Hiển thị giá trị bên trên cột
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(480, 500)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);
    }

}

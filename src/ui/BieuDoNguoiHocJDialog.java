package ui;

import dao.KhoaHocDAO;
import dao.ThongKeDAO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class BieuDoNguoiHocJDialog extends ApplicationFrame {

    ThongKeDAO tkDAO = new ThongKeDAO();

    public BieuDoNguoiHocJDialog(boolean modal, String title) {
        super(title);

        DefaultPieDataset dataset = new DefaultPieDataset();
        // Set dữ liệu
        List<Object[]> list = tkDAO.getDiemChuyenDe();
        for (Object[] row : list) {
            dataset.setValue(row[0].toString(), (Number) row[1]);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Biểu đồ thể hiện số lượng người học theo chuyên đề",
                dataset,
                true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})")); // Hiển thị tên và phần trăm

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 500)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);

        
    }
}

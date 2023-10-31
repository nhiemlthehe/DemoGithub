package ui;

import dao.KhoaHocDAO;
import dao.ThongKeDAO;
import entity.KhoaHoc;
import utils.Auth;
import utils.MsgBox;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class ThongKeJDialog extends javax.swing.JDialog {

    ThongKeDAO tkDAO = new ThongKeDAO();
    KhoaHocDAO khDAO = new KhoaHocDAO();
    BieuDoNguoiHocJDialog chart2;
    BieuDoDoanhThuJDialog chart1;

    public ThongKeJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        setTitle("Statistics");
        setLocationRelativeTo(parent);
    }

    void init() {
        fillComboYear();
        fillComboKhoaHoc();
        fillTableBangDiem();
        fillTableDiemChuyenDe();
        fillTableNguoiHoc();
        fillTableDoanhThu();
        this.selectTab(0);
        if (!Auth.isManager()) {
            tabs.remove(3);
        }
    }

    public void selectTab(int index) {
        tabs.setSelectedIndex(index);
    }

    void fillTableBangDiem() {
        DefaultTableModel model = (DefaultTableModel) tblScoreBoard.getModel();
        model.setRowCount(0);
        KhoaHoc kh = (KhoaHoc) cboCourse.getSelectedItem();
        List<Object[]> list = tkDAO.getBangDiem(kh.getMaKH());
        for (Object[] row : list) {
            double score = (double) row[2];
            model.addRow(new Object[]{row[0], row[1], score, getXepLoai(score)});
        }
    }

    void fillTableNguoiHoc() {
        DefaultTableModel model = (DefaultTableModel) tblLearners.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkDAO.getLuongNguoiHoc();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    void fillTableDiemChuyenDe() {
        DefaultTableModel model = (DefaultTableModel) tblSubjectScore.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkDAO.getDiemChuyenDe();
        for (Object[] row : list) {
            model.addRow(new Object[]{row[0], row[1], row[2], row[3], new DecimalFormat("####0.00").format(row[4])});
        }
    }

    void fillComboYear() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboYear.getModel();
        model.removeAllElements();
        List<Integer> list = khDAO.selectYears();
        for (Integer year : list) {
            model.addElement(year);
        }
    }

    void fillTableDoanhThu() {
        DefaultTableModel model = (DefaultTableModel) tblRevenue.getModel();
        model.setRowCount(0);
        int year = (Integer) cboYear.getSelectedItem();
        List<Object[]> list = tkDAO.getDoanhThu(year);
        DecimalFormat format = new DecimalFormat("###,###.##");
        for (Object[] row : list) {
            model.addRow(new Object[]{row[0], row[1], row[2], format.format(row[3]),
                format.format(row[4]), format.format(row[5]), format.format(row[6])});
        }
    }

    void fillComboKhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCourse.getModel();
        model.removeAllElements();
        List<KhoaHoc> list = khDAO.selectAll();
        for (KhoaHoc kh : list) {
            model.addElement(kh);
        }
    }

    String getXepLoai(double score) {
        if (score < 5) {
            return "Yếu";
        } else if (score < 6.5) {
            return "Trung bình";
        } else if (score < 7.5) {
            return "Khá";
        } else if (score < 9) {
            return "Giỏi";
        }
        return "Xuất sắc";
    }

    public void printReport(JTable table, File file) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < table.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(table.getValueAt(i, j).toString());
            }
        }

        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

        if (MsgBox.confirm(this, "In thành công, bạn muốn mở nó không?")) {
            Desktop.getDesktop().browse(file.toURI());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlScoreBoard = new javax.swing.JPanel();
        lblCourseTitle = new javax.swing.JLabel();
        cboCourse = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblScoreBoard = new javax.swing.JTable();
        pnlLearners = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLearners = new javax.swing.JTable();
        btnCloseC = new javax.swing.JButton();
        btnChart = new javax.swing.JButton();
        pnlSubjectScore = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSubjectScore = new javax.swing.JTable();
        pnlRevenue = new javax.swing.JPanel();
        lblYear = new javax.swing.JLabel();
        cboYear = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRevenue = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnViewChart = new javax.swing.JButton();
        btnCloseChart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setBackground(new java.awt.Color(3, 70, 70));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 204, 0));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("TỔNG HỢP THỐNG KÊ");

        pnlScoreBoard.setBackground(new java.awt.Color(3, 84, 84));

        lblCourseTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblCourseTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCourseTitle.setText("Khóa học");

        cboCourse.setBackground(new java.awt.Color(255, 255, 255));
        cboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCourseActionPerformed(evt);
            }
        });

        tblScoreBoard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã người học", "Họ tên", "Điểm", "Xếp loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblScoreBoard);

        javax.swing.GroupLayout pnlScoreBoardLayout = new javax.swing.GroupLayout(pnlScoreBoard);
        pnlScoreBoard.setLayout(pnlScoreBoardLayout);
        pnlScoreBoardLayout.setHorizontalGroup(
            pnlScoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScoreBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboCourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
        pnlScoreBoardLayout.setVerticalGroup(
            pnlScoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScoreBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlScoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCourseTitle)
                    .addComponent(cboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
        );

        tabs.addTab("Bảng điểm", pnlScoreBoard);

        jScrollPane2.setBackground(new java.awt.Color(3, 84, 84));

        tblLearners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Năm", "Số người học", "Đăng kí sớm nhất", "Đăng kí muôn nhất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblLearners);

        btnCloseC.setBackground(new java.awt.Color(204, 255, 255));
        btnCloseC.setText("Đóng");
        btnCloseC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseCActionPerformed(evt);
            }
        });

        btnChart.setBackground(new java.awt.Color(204, 255, 255));
        btnChart.setText("Xem biểu đồ");
        btnChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLearnersLayout = new javax.swing.GroupLayout(pnlLearners);
        pnlLearners.setLayout(pnlLearnersLayout);
        pnlLearnersLayout.setHorizontalGroup(
            pnlLearnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLearnersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCloseC, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlLearnersLayout.setVerticalGroup(
            pnlLearnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLearnersLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLearnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChart)
                    .addComponent(btnCloseC))
                .addContainerGap())
        );

        tabs.addTab("Người học", pnlLearners);

        tblSubjectScore.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Chuyên đề", "Số người học", "Điểm thấp nhất", "Điểm cao nhất", "Trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblSubjectScore);

        javax.swing.GroupLayout pnlSubjectScoreLayout = new javax.swing.GroupLayout(pnlSubjectScore);
        pnlSubjectScore.setLayout(pnlSubjectScoreLayout);
        pnlSubjectScoreLayout.setHorizontalGroup(
            pnlSubjectScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
        pnlSubjectScoreLayout.setVerticalGroup(
            pnlSubjectScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubjectScoreLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        tabs.addTab("Điểm chuyên đề", pnlSubjectScore);

        pnlRevenue.setBackground(new java.awt.Color(3, 84, 84));

        lblYear.setForeground(new java.awt.Color(255, 255, 255));
        lblYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYear.setText("Năm");

        cboYear.setBackground(new java.awt.Color(255, 255, 255));
        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        tblRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Môn học", "Số khóa", "Số học viên", "Doanh thu", "Thấp nhấp", "Cao nhất", "Trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblRevenue);

        btnPrint.setBackground(new java.awt.Color(153, 255, 255));
        btnPrint.setText("In");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnViewChart.setBackground(new java.awt.Color(153, 255, 255));
        btnViewChart.setText("Xem biểu đồ");
        btnViewChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewChartActionPerformed(evt);
            }
        });

        btnCloseChart.setBackground(new java.awt.Color(153, 255, 255));
        btnCloseChart.setText("Đóng");
        btnCloseChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRevenueLayout = new javax.swing.GroupLayout(pnlRevenue);
        pnlRevenue.setLayout(pnlRevenueLayout);
        pnlRevenueLayout.setHorizontalGroup(
            pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRevenueLayout.createSequentialGroup()
                .addGroup(pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRevenueLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewChart, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCloseChart, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRevenueLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlRevenueLayout.setVerticalGroup(
            pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRevenueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYear)
                    .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewChart)
                    .addComponent(btnCloseChart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        tabs.addTab("Doanh thu", pnlRevenue);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseChartActionPerformed
        chart1.setVisible(false);
    }//GEN-LAST:event_btnCloseChartActionPerformed

    private void btnViewChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewChartActionPerformed
        if (chart1 != null) {
            chart1.setVisible(false);
        }
        int year = Integer.parseInt(cboYear.getSelectedItem() + "");
        chart1 = new BieuDoDoanhThuJDialog(rootPaneCheckingEnabled, "Biểu đồ doanh thu năm " + year, year);
        chart1.setAlwaysOnTop(true);
        chart1.pack();
        chart1.setLocation(1030, 0);
        chart1.setVisible(true);
    }//GEN-LAST:event_btnViewChartActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", ".xls", ".xlsx", ".xln");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save As");
        int value = chooser.showSaveDialog(null);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                printReport(tblRevenue, file);
            } catch (IOException ex) {
                Logger.getLogger(ThongKeJDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }//GEN-LAST:event_btnPrintActionPerformed

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
        fillTableDoanhThu();
    }//GEN-LAST:event_cboYearActionPerformed

    private void btnChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartActionPerformed
        if (chart2 != null) {
            chart2.setVisible(false);
        }
        chart2 = new BieuDoNguoiHocJDialog(rootPaneCheckingEnabled, "Biểu đồ số lượng người học");
        chart2.setAlwaysOnTop(true);
        chart2.pack();
        chart2.setLocation(0, 0);
        chart2.setVisible(true);
    }//GEN-LAST:event_btnChartActionPerformed

    private void cboCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCourseActionPerformed
        fillTableBangDiem();
    }//GEN-LAST:event_cboCourseActionPerformed

    private void btnCloseCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseCActionPerformed
        chart2.setVisible(false);
    }//GEN-LAST:event_btnCloseCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongKeJDialog dialog = new ThongKeJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChart;
    private javax.swing.JButton btnCloseC;
    private javax.swing.JButton btnCloseChart;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnViewChart;
    private javax.swing.JComboBox<String> cboCourse;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCourseTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblYear;
    private javax.swing.JPanel pnlLearners;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRevenue;
    private javax.swing.JPanel pnlScoreBoard;
    private javax.swing.JPanel pnlSubjectScore;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLearners;
    private javax.swing.JTable tblRevenue;
    private javax.swing.JTable tblScoreBoard;
    private javax.swing.JTable tblSubjectScore;
    // End of variables declaration//GEN-END:variables
}

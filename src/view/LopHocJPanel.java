package view;

import javax.swing.JPanel;

import controller.LopHocController;
import controller.QuanLyLopHocController;

public class LopHocJPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public LopHocJPanel() {
		initComponents();
		QuanLyLopHocController controller=new QuanLyLopHocController(jpnView, btnAdd, jtfSearch);
		controller.setDataToTable();
		controller.setEvent();
		
		
	}
	

		    private void initComponents() {

		        jpnRoot = new javax.swing.JPanel();
		        btnAdd = new javax.swing.JButton();
		        jtfSearch = new javax.swing.JTextField();
		        jpnView = new javax.swing.JPanel();

		        jpnRoot.setBackground(new java.awt.Color(240, 240, 240));

		        btnAdd.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
		        btnAdd.setText("Thêm mới");

		        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
		        jpnView.setLayout(jpnViewLayout);
		        jpnViewLayout.setHorizontalGroup(
		            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGap(0, 0, Short.MAX_VALUE)
		        );
		        jpnViewLayout.setVerticalGroup(
		            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGap(0, 520, Short.MAX_VALUE)
		        );

		        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
		        jpnRoot.setLayout(jpnRootLayout);
		        jpnRootLayout.setHorizontalGroup(
		            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnRootLayout.createSequentialGroup()
		                .addContainerGap()
		                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                    .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .addGroup(jpnRootLayout.createSequentialGroup()
		                        .addComponent(jtfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 509, Short.MAX_VALUE)
		                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
		                .addGap(21, 21, 21))
		        );
		        jpnRootLayout.setVerticalGroup(
		            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(jpnRootLayout.createSequentialGroup()
		                .addGap(6, 6, 6)
		                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                    .addComponent(jtfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGap(19, 19, 19)
		                .addComponent(jpnView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addContainerGap(153, Short.MAX_VALUE))
		        );

		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		        this.setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addContainerGap()
		                .addComponent(jpnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addContainerGap())
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addContainerGap()
		                .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		        );
		    }// </editor-fold>//GEN-END:initComponents


		    // Variables declaration - do not modify//GEN-BEGIN:variables
		    private javax.swing.JButton btnAdd;
		    private javax.swing.JPanel jpnRoot;
		    private javax.swing.JPanel jpnView;
		    private javax.swing.JTextField jtfSearch;}

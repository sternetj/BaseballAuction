import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;


public class GetPlayers extends Thread {

	JTableX tTable;
	int tId;
	String last = "";
	
	public GetPlayers(JTableX teamTable, int id) {
		tTable = teamTable;
		tId = id;
	}
	
	@Override
	public void run() {
		while (true) {
			String code = Utils.GET("getTeamPlayers?teamid=" + tId);
			if (last != code) {
				last = code;
				String[] players = (code.split(";"));
				ArrayList<String> c = new ArrayList<String>();
				ArrayList<String> b1 = new ArrayList<String>();
				ArrayList<String> b2 = new ArrayList<String>();
				ArrayList<String> ss = new ArrayList<String>();
				ArrayList<String> b3 = new ArrayList<String>();
				ArrayList<String> of = new ArrayList<String>();
				ArrayList<String> p = new ArrayList<String>();
				ArrayList<String> ut = new ArrayList<String>();
				ArrayList<String> cm = new ArrayList<String>();
				ArrayList<String> mi = new ArrayList<String>();
				for( int i = 0; i < players.length; i++){
					String[] fields = players[i].split(",");
					switch (fields[1]){
					case "C":
						c.add(fields[0]);
						ut.add(fields[0]);
						cm.add(fields[0]);
					break;
					case "1B":
						b1.add(fields[0]);
						ut.add(fields[0]);
						cm.add(fields[0]);
					break;
					case "2B":
						b2.add(fields[0]);
						ut.add(fields[0]);
						mi.add(fields[0]);
					break;
					case "SS":
						ss.add(fields[0]);
						ut.add(fields[0]);
						mi.add(fields[0]);
					break;
					case "3B":
						b3.add(fields[0]);
						ut.add(fields[0]);
						cm.add(fields[0]);
					break;
					case "OF":
						of.add(fields[0]);
						ut.add(fields[0]);
					break;
					case "P":
						p.add(fields[0]);
						ut.add(fields[0]);
					break;
					}
				}
				TableColumn playerCol = tTable.getColumnModel().getColumn(1);
				DefaultTableCellRenderer renderer =
		                new DefaultTableCellRenderer();
		        renderer.setToolTipText("Click for combo box");
		        playerCol.setCellRenderer(renderer);
		        
				RowEditorModel rm = new RowEditorModel();
				tTable.setRowEditorModel(rm);
				//catchers
				addCombo(c, rm, 0);
				
				//1B
				addCombo(b1, rm, 1);
				
				//2B
				addCombo(b2, rm, 2);
				
				//3B
				addCombo(b3, rm, 3);
				
				//SS
				addCombo(ss, rm, 4);
				
				//CM
				addCombo(cm, rm, 5);
				
				//MI
				addCombo(mi, rm, 6);
				
				//OF
				addCombo(of, rm, 7);
				addCombo(of, rm, 8);
				addCombo(of, rm, 9);
				addCombo(of, rm, 10);
				
				//Ut
				addCombo(ut, rm, 11);
				addCombo(ut, rm, 12);
				
				//P
				addCombo(p, rm, 13);
				addCombo(p, rm, 14);
				addCombo(p, rm, 15);
				addCombo(p, rm, 16);
				addCombo(p, rm, 17);
				addCombo(p, rm, 18);
				addCombo(p, rm, 19);
				addCombo(p, rm, 20);
				addCombo(p, rm, 21);
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// table.setValueAt(count++, 0, 0);
		}
	}
	
	private void addCombo(ArrayList<String> players, RowEditorModel rm, int row){
		JComboBox<String> combo = new JComboBox<String>(players.toArray(new String[players.size()]));
		rm.addEditorForRow(row, new DefaultCellEditor(combo));
	}


}

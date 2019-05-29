public class Show_CF {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				ConnectFOURGUI gui = new ConnectFOURGUI();
			}
		});
	}
}
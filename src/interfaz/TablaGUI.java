package interfaz;

public interface TablaGUI<E> {
	
		public void aggregarDatoTabla(String[] filaArr);

		public void actualizarDatosTabla(String id, E[][] datos);
}

package paqueteD;

import java.util.ArrayList;

public class Xy {
	private ArrayList<ODeportista> mujeres;
	private ArrayList<ODeportista> hombres;

	public void setALmujeres(ArrayList<ODeportista> newALmujeres) {
		this.mujeres = newALmujeres;
	}
	
	public void setALhombres(ArrayList<ODeportista> newALhombres) {
		this.hombres = newALhombres;
	}
	
	public ArrayList<ODeportista> getALmujeres(){
		return this.mujeres;
	}
	
	public ArrayList<ODeportista> getALhombres(){
		return this.hombres;
	}
}

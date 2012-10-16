package org.sysu.igem.riboswitch;

public class freeEnergy {
	protected double body;
	protected double erro;
	public freeEnergy(double body,double erro){
		this.body=body;
		this.erro=erro;
	}
	public freeEnergy add(freeEnergy input){
		double body=this.body+input.getBody();
		double erro=this.erro+input.getErro();
		freeEnergy resualt= new freeEnergy(body,erro);
		return resualt;
	}
	public freeEnergy minus(freeEnergy input){
		double body=this.body-input.getBody();
		double erro=this.erro-input.getErro();
		freeEnergy resualt= new freeEnergy(body,erro);
		return resualt;
	}
	public double getBody(){
		return body;
	}
	public double getErro(){
		return erro;
	}
}

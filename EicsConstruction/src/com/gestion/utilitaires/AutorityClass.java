package com.gestion.utilitaires;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class AutorityClass implements Serializable{
private static final long serialVersionUID = 1L;
private boolean menuAdmin = false;
private boolean menucreation = false;
private boolean menuImputation = false;
private boolean menuExecution = false;
public boolean isMenuAdmin() {
	return menuAdmin;
}
public void setMenuAdmin(boolean menuAdmin) {
	this.menuAdmin = menuAdmin;
}
public boolean isMenucreation() {
	return menucreation;
}
public void setMenucreation(boolean menucreation) {
	this.menucreation = menucreation;
}
public boolean isMenuImputation() {
	return menuImputation;
}
public void setMenuImputation(boolean menuImputation) {
	this.menuImputation = menuImputation;
}
public boolean isMenuExecution() {
	return menuExecution;
}
public void setMenuExecution(boolean menuExecution) {
	this.menuExecution = menuExecution;
}



}

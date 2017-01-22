package com.psi.calendar;

import java.io.Serializable;

/**
 * Created by navi on 1/22/17.
 */
class Sesion implements Serializable{

    private int hora;
    private int duracion;
    private int dia;
    private String nombre;
    private String tipoGrupo;

    public Sesion(){}

    public Sesion(int hora, int duracion, int dia, String nombre, String tipo){
        this.hora = hora;
        this.duracion = duracion;
        this.dia = dia;
        this.nombre = nombre;
        this.tipoGrupo = tipo;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(String tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public int getDia(){
        return this.dia;
    }

    public void setDia(int dia){
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Sesion{" +
                "hora=" + hora +
                ", duracion=" + duracion +
                ", dia=" + dia +
                ", nombre='" + nombre + '\'' +
                ", tipoGrupo='" + tipoGrupo + '\'' +
                '}';
    }
}

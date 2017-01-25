package com.psi.calendar;

import java.util.Vector;

public class GrupoClase {
    private String nombre;
    private String id;
    private Vector<Integer> horaInicio;
    private Vector<Integer> duracion;
    private Vector<Integer> dia;      //Campo nuevo. De tipo entero. Indica el día del 1 al 7 (o del 0 al 6, ahora mismo tengo mis dudas)
    private String tipo;
    private String grupo;
    private int numHoras;

    /*
    * Constructores
    */

    public GrupoClase() {
    }

    public GrupoClase(String nombre, String tipo, String grupo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.grupo = grupo;
        this.horaInicio = new Vector<>(100);
        this.duracion = new Vector<>(100);
        this.dia = new Vector<>(100);
    }

    public GrupoClase(String nombre, String id, int horaInicio, int duracion, int dia, String tipo) {
        this.nombre = nombre;
        this.id = id;
        this.horaInicio = new Vector<>(100);
        this.duracion = new Vector<>(100);
        this.dia = new Vector<>(100);
        this.horaInicio.add(horaInicio);
        this.duracion.add(duracion);
        this.dia.add(dia);
        this.numHoras++;
        this.tipo = tipo;
    }

    /*
    * Getters & Setters
    */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addGrupo(int horaInicio, int duracion, int dia){
        this.horaInicio.add(horaInicio);
        this.duracion.add(duracion);
        this.dia.add(dia);
        this.numHoras += duracion;
    }

    public String getDatosGrupo(int i){
        int hora = this.horaInicio.get(i);
        int dia = this.dia.get(i);
        int dur = this.duracion.get(i);
        String ret = (hora+"#"+dia+"#"+dur);
        return ret;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setGrupo(String grupo){
        this.grupo = grupo;
    }

    public String getGrupo(){
        return this.grupo;
    }

    public void addHoras(){
        this.numHoras++;
    }

    public int getNumHoras(){
        return this.numHoras;
    }

    public Vector<Integer> getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Vector<Integer> horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Vector<Integer> getDuracion() {
        return duracion;
    }

    public void setDuracion(Vector<Integer> duracion) {
        this.duracion = duracion;
    }

    public Vector<Integer> getDia() {
        return dia;
    }

    public void setDia(Vector<Integer> dia) {
        this.dia = dia;
    }

}

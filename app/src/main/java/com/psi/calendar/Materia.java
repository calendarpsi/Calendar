package com.psi.calendar;

import java.util.TreeMap;

public class Materia {
    private String nombre;
    private int id;
    private String siglas;
    //private ArrayList<TipoClase> tiposClases; //Prescindible. Escribiendo código me he dado cuenta de que es mucho mejor tener un arraylist para grupos.

    //PRIVATE INT TIPOSGRUPO = 2;

    //Es incluso cuestionable si es mejor tener ArrayList o TreeMap con entradas del tipo <String, GrupoClase> donde String ser������a TA (Teor������a A) o B02 (Grupo B 02) por ejemplo
    private TreeMap<Integer,GrupoClase> gruposT;
    private TreeMap<Integer,GrupoClase> gruposB;
    private TreeMap<Integer,GrupoClase> gruposC;

    /*
    * Constructores
    */
    public Materia() {
    }

    public Materia(int id, String nombre, String siglas) {
        this.id = id;
        this.siglas = siglas;
        this.nombre = nombre;
        this.gruposT = new TreeMap<Integer, GrupoClase>();
        this.gruposB = new TreeMap<Integer, GrupoClase>();
        this.gruposC = new TreeMap<Integer, GrupoClase>();
    }

    public Materia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Materia(String nombre, String siglas){
        this.nombre = nombre;
        this.siglas = siglas;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
/*
    public ArrayList<TipoClase> getTiposClases() {
        return tiposClases;
    }

    public void setTiposClases(ArrayList<TipoClase> tiposClases) {
        this.tiposClases = tiposClases;
    }
*/
    public void addGrupoT(Integer clase, GrupoClase grupo){
        this.gruposT.put(clase, grupo);
    }

    public void addGrupoB(Integer clase, GrupoClase grupo){
        this.gruposB.put(clase, grupo);
    }
    
    public void addGrupoC(Integer clase, GrupoClase grupo){
        this.gruposC.put(clase, grupo);
    }

    public TreeMap<Integer, GrupoClase> getGruposT(){
        return this.gruposT;
    }

    public TreeMap<Integer, GrupoClase> getGruposB(){
        return this.gruposB;
    }

    public TreeMap<Integer, GrupoClase> getGruposC(){
        return this.gruposC;
    }

    public int cantGrupos(){
        int cantGrupos = 0;
        if(!this.gruposT.isEmpty()) cantGrupos++;
        if(!this.gruposB.isEmpty()) cantGrupos++;
        if(!this.gruposC.isEmpty()) cantGrupos++;
        return cantGrupos;
    }

    public int[] maximos(){
        int sizeVector = this.cantGrupos();
        int[] maximos = new int[sizeVector];
        int i = 0;
        if(!this.gruposT.isEmpty()){
            maximos[i] = gruposT.size();
            i++;
        }
        if(!this.gruposB.isEmpty()){
            maximos[i] = gruposB.size();
            i++;
        }
        if(!this.gruposC.isEmpty()){
            maximos[i] = gruposC.size();
            i++;
        }
        return maximos;
    }

    public TreeMap<Integer, GrupoClase> checkTipoGrupo(int id){
        int i = 0;
        if(!this.gruposT.isEmpty()){
            if(id == i){
                return this.getGruposT();
            }else i++;
        }
        if(!this.gruposB.isEmpty()){
            if(id == i){
                return this.getGruposB();
            }else i++;
        }
        return this.getGruposC(); //unreachable code, ��No?
    }

    public int gruposTSize(){
        return this.gruposT.size();
    }

}//fin clase Materia

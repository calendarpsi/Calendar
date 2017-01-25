package com.psi.calendar;
/*
 * Como es un algoritmo evolutivo los genes van a ser las elecciones de grupos y dem��s, as�� que su n��mero variar�� y se obtendr��
 * en funci��n de cu��ntas opciones de horarios haya.
 * Cada HORARIO contiene MATERIAS:
 * 	Cada MATERIA contiene varios TIPOS DE CLASES (y puede que tengas que ir al mismo grupo seg��n el tipo de clase o no):
 *   Cada TIPO DE CLASE contiene varios GRUPOS.
 */

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


public class Horario {
    private Horario horario;
    private TreeMap<Integer, Materia> Materias = new TreeMap<>();
    private TreeMap<Integer, Integer> MateriasDeInteres = new TreeMap<>();
    private int h_inicio = 0, h_fin = 23;
    private int[] dias_laborables = {1, 1, 1, 1, 1};    //datoAPI
    private int[][] mat_disponibilidad = new int[h_fin - h_inicio][5];
    private int[] genRule = null; //AÑADIDO MANUALMENTE GITHUB
    private int cantGrupos;

    boolean mañana = true;

    public void main(List<String> raw_input) throws ParseException {
        primerCuatri();
        for (int i = 0; i < raw_input.size(); i++) {
            generarObjetos(raw_input.get(i));
        }
        this.Materias = Materias;
        this.cantGrupos = 0;
        //this.cantGrupos = getCantGrupos();
    }

    public void crearAsignaturasInteres(int[] asigInteres){
        for (int i = 0; i < asigInteres.length; i++) {
            MateriasDeInteres.put(i,asigInteres[i]);
        }
        this.cantGrupos = getCantGrupos();
    }

    public int[][] crearMat_disp() {
        int x, y;
        for (x = 0; x < h_fin - h_inicio; x++) { //FILAS=HORAS: el horario comienza la las h_inicio y termina a las h_fin
            for (y = 0; y < 5; y++) {//COLUMNAS=D��AS: los 5 d��as de la semana.
                if (dias_laborables[y] == 0) {
                    mat_disponibilidad[x][y] = -1;
                } else {
                    if (mañana == true) {
                      /*
                        if (Math.random() < 0.4) {
                            mat_disponibilidad[x][y] = 0;
                        } else {
                            mat_disponibilidad[x][y] = 1;
                        }
                        */
                    } else {
                        mat_disponibilidad[x][y] = 0;
                    }
                }//fin else
            }//fin for col
        }//fin for fil
        return mat_disponibilidad;
    }

    public int[][] crearMat_vacia() {
        int x, y;
        int mat_disponibilidad_vac[][] = new int[h_fin - h_inicio][5];
        for (x = 0; x < h_fin - h_inicio; x++) {
            for (y = 0; y < 5; y++) {
                mat_disponibilidad[x][y] = 0;
            }
        }
        return mat_disponibilidad_vac;
    }

    public void pregunta1(int huecos) {
        /*
        * Cuántas horas quieres tener libres entre clases : 0, 1, 2, 3 o 4
        */

    }

    public void pregunta2(int eleccion) {
        /*
        * Prefieres tener clase por la mañana o por la tarde : mañana++, mañana+, tarde+, tarde++
        * 1: I prefer having classes in the morning.
        * 2: I prefer in the morning rather than in the afternoon.
        * 3: I prefer in the afternoon rather than in the morning.
        * 4: I prefer having classes in the afternoon.
        */
        switch (eleccion) {
            case 1: //mañana++
                for (int i = 9; i < 15; i++) {//TODO dejar que el usuario elija que es la mañana y que es la tarde
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] += 5;
                    }
                }
                for (int i = 15; i < 20; i++) {
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] -= 5;
                    }
                }
                break;
            case 2: //mañana+
                for (int i = 9; i < 15; i++) {
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] += 3;
                    }
                }
                for (int i = 15; i < 20; i++) {
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] -= 3;
                    }
                }
                break;
            case 3: //tarde+
                for (int i = 9; i < 15; i++) {//TODO dejar que el usuario elija que es la mañana y que es la tarde
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] -= 3;
                    }
                }
                for (int i = 15; i < 20; i++) {
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] += 3;
                    }
                }
                break;
            case 4: //tarde++
                for (int i = 9; i < 15; i++) {//TODO dejar que el usuario elija que es la mañana y que es la tarde
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] -= 5;
                    }
                }
                for (int i = 15; i < 20; i++) {
                    for (int j = 0; j < 5; j++) {
                        mat_disponibilidad[i - h_inicio][j] += 5;
                    }
                }
                break;
            default:
                break;
        }
    }

    public void pregunta3(ArrayList<String> respuesta3) {//TODO ¿habría que poner positivos y no solo negativos... retocar?
        /*
        * Quieres tener algún día libre : lu, ma, mi, ju, vi (checkbox?)
        *
        */
        for (int i = 0; i < respuesta3.size(); i++) {
            switch (respuesta3.get(i)) {
                case "lu":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][0] -= 20;
                    }
                    break;
                case "ma":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][1] -= 20;
                    }
                    break;
                case "mi":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][2] -= 20;
                    }
                    break;
                case "ju":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][3] -= 20;
                    }
                    break;
                case "vi":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][4] -= 20;
                    }
                    break;
                case "ev":
                    for (int j = h_inicio; j < h_fin - h_inicio; j++) {
                        mat_disponibilidad[j][0] -= 20;
                        mat_disponibilidad[j][1] -= 20;
                        mat_disponibilidad[j][2] -= 20;
                        mat_disponibilidad[j][3] -= 20;
                        mat_disponibilidad[j][4] -= 20;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void pregunta4(int eleccion) {
        /*
        * A qué hora quieres comer : 13h o 14h
        */
        for (int i = 0; i < 5; i++) {
            mat_disponibilidad[eleccion][i] = -100;
        }
    }

    //Ignoramos la pregunta 5, nos ajustamos solo al primer cuatrimestre.

    public void pregunta5(ArrayList<String> respuesta5) {
        /*
        * Selecciona las asignaturas de tu primer semestre. El array puede contener
        * AO
        * CAL1
        * FMT
        * ALG
        * EMP
        */

        //Destinada a seleccionar el horario del curso

    }

    public void primerCuatri() {
        Materia AO = new Materia(1, "Arquitectura de Ordenadores", "AO");
        Materia CAL1 = new Materia(2, "Calculo I", "CAL-I");
        Materia FMT = new Materia(3, "Fundamentos da Mecánica e Termodinámica", "FMT");
        Materia ALG = new Materia(4, "Algebra", "ALG");
        Materia EMP = new Materia(5, "Fundamentos de Empresas", "EMP");
        Materia CD = new Materia(6, "Comunicacion de datos", "CD");
        Materia FE = new Materia(7, "Fundamentos de Electronica", "FE");
        Materia PII = new Materia(8, "Programacion II", "PII");
        Materia TEM = new Materia(9, "Transmision Electromagnetica", "TEM");
        Materia PDS = new Materia(10, "Procesado Digital de Senhales", "PDS");
        this.Materias.put(1, AO);
        this.Materias.put(2, CAL1);
        this.Materias.put(3, FMT);
        this.Materias.put(4, ALG);
        this.Materias.put(5, EMP);
        this.Materias.put(6, CD);
        this.Materias.put(7, FE);
        this.Materias.put(8, PII);
        this.Materias.put(9, TEM);
        this.Materias.put(10, PDS);


    }

   /*

   Este es un ejemplo de la salida del calendario:

*/

    public void generarObjetos(String datos) throws ParseException {
        String asignatura[] = datos.split(" ");
        switch (asignatura[0]) {

            /*
                Las asignaturas están en un Map<Int, Materia> de modo que sabemos qué materia tiene cada ID
                En función del texto creamos un grupo (de forma genérica) y lo añadimos a esa asignatura
            */

            case "AO":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("AO", datos);
                    Materia mat = Materias.get(1);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("AO", datos);
                    Materia mat = Materias.get(1);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "CAL-I":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Calculo I", datos);
                    Materia mat = Materias.get(2);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Calculo I", datos);
                    Materia mat = Materias.get(2);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "FMT":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("FMT", datos);
                    Materia mat = Materias.get(3);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("FMT", datos);
                    Materia mat = Materias.get(3);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "EMP":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Empresas", datos);
                    Materia mat = Materias.get(4);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Empresas", datos);
                    Materia mat = Materias.get(4);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "ALG":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Algebra", datos);
                    Materia mat = Materias.get(5);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Algebra", datos);
                    Materia mat = Materias.get(5);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "CD":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Comunicacion de datos", datos);
                    Materia mat = Materias.get(6);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Comunicacion de datos", datos);
                    Materia mat = Materias.get(6);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "FE":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Fundamentos de electronica", datos);
                    Materia mat = Materias.get(7);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Fundamentos de electronica", datos);
                    Materia mat = Materias.get(7);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "PII":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Programacion II", datos);
                    Materia mat = Materias.get(8);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Programacion II", datos);
                    Materia mat = Materias.get(8);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "TEM":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Transmision Electromagnetica", datos);
                    Materia mat = Materias.get(9);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Transmision Electromagnetica", datos);
                    Materia mat = Materias.get(9);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            case "PDS":
                if (asignatura[1].contains("TEORIA")) {
                    GrupoClase grupo = creaGrupoT("Procesado Digital de Senhales", datos);
                    Materia mat = Materias.get(10);
                    if (grupo != null) {
                        if (grupo.getGrupo().equals("A")) {
                            mat.addGrupoT(1, grupo);
                        } else if (grupo.getGrupo().equals("B")) {
                            mat.addGrupoT(2, grupo);
                        } else if (grupo.getGrupo().equals("C")) {
                            mat.addGrupoT(3, grupo);
                        }
                    }
                } else {
                    GrupoClase grupo = creaGrupoB("Procesado Digital de Senhales", datos);
                    Materia mat = Materias.get(10);
                    mat.addGrupoB(Integer.parseInt(grupo.getId().substring(1)), grupo);
                }
                break;
            default:
                break;
        }
    }

    /*
        Método para generar un grupo de tipo "Teoría" y retornarlo a donde se le llame.
        CAL-I (TEORIA)#2017-01-26T11:00:00.000+01:00#2017-01-26T12:00:00.000+01:00
    */


    public GrupoClase creaGrupoT(String asignatura, String rawData) throws ParseException {
        Boolean check = false;
        Materia mat = null;
        String campos[] = rawData.split("#");
        String fechas = campos[1] + "#" + campos[2];
        String almohadillas[] = fechas.split("#");
        String fechaInicio = almohadillas[0];
        String fechaFin = almohadillas[1];
        String camposFecha[] = fechaInicio.split("T");
        String camposFechaFin[] = fechaFin.split("T");
        String valores[] = rawData.split(" ");
        String grupo = Character.toString(valores[2].charAt(0));
        Calendar c = Calendar.getInstance();
        Date day = new SimpleDateFormat("yyyy-mm-dd").parse(camposFecha[0]);
        c.setTime(day);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        int duracion = Integer.parseInt(camposFechaFin[1].substring(0, 2)) - Integer.parseInt(camposFecha[1].substring(0, 2));

        switch (asignatura) {
            case "AO":
                mat = Materias.get(1);
                TreeMap<Integer, GrupoClase> mapAO = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapAO.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapAO = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Calculo I":
                mat = Materias.get(2);
                TreeMap<Integer, GrupoClase> mapCAL = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapCAL.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapCAL = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "FMT":
                mat = Materias.get(3);
                TreeMap<Integer, GrupoClase> mapFMT = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapFMT.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapFMT = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Empresas":
                mat = Materias.get(4);
                TreeMap<Integer, GrupoClase> mapEMP = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapEMP.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapEMP = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Algebra":
                mat = Materias.get(5);
                TreeMap<Integer, GrupoClase> mapALG = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapALG.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Comunicacion de datos":
                mat = Materias.get(6);
                TreeMap<Integer, GrupoClase> mapCD = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapCD.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Fundamentos de electronica":
                mat = Materias.get(7);
                TreeMap<Integer, GrupoClase> mapFE = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapFE.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Programacion II":
                mat = Materias.get(8);
                TreeMap<Integer, GrupoClase> mapPII = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapPII.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Transmision Electromagnetica":
                mat = Materias.get(9);
                TreeMap<Integer, GrupoClase> mapTEM = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapTEM.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
            case "Procesado Digital de Senhales":
                mat = Materias.get(10);
                TreeMap<Integer, GrupoClase> mapPDS = mat.getGruposT();
                check = false;
                for (Map.Entry<Integer, GrupoClase> entry : mapPDS.entrySet()) {
                    GrupoClase grupoCheck = entry.getValue();
                    if (grupoCheck.getGrupo().equals(grupo)) {
                        grupoCheck.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                        check = true;
                        mapALG = null;
                        return null;
                    }
                }
                if (!false) {
                    GrupoClase grupoNew = new GrupoClase("Teoria " + asignatura, "T", grupo);
                    grupoNew.addGrupo(Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek);
                    return grupoNew;
                }
                break;
        }
        return null;
    }

    /*
        Método para generar un grupo de tipo "Práctica" y retornarlo a donde se le llame.
        CAL-I (PRACTICA) B01#2017-01-23T09:00:00.000+01:00#2017-01-23T10:00:00.000+01:00
    */

    // AO (PRACTICA) B09#2017-01-25T11:00:00.000+01:00#2017-01-25T13:00:00.000+01:00
    public GrupoClase creaGrupoB(String asignatura, String rawData) throws ParseException {
        String campos[] = rawData.split("#");
        String fechas = campos[1] + "#" + campos[2];
        String almohadillas[] = fechas.split("#");
        String fechaInicio = almohadillas[0];
        String fechaFin = almohadillas[1];
        String camposFecha[] = fechaInicio.split("T");
        String camposFechaFin[] = fechaFin.split("T");
        Calendar c = Calendar.getInstance();
        Date day = new SimpleDateFormat("yyyy-mm-dd").parse(camposFecha[0]);
        c.setTime(day);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        //String grupoID = valores[2].substring(0, 3);
        String grupoID = campos[0].substring(campos[0].length() - 3, campos[0].length());
        int duracion = Integer.parseInt(camposFechaFin[1].substring(0, 2)) - Integer.parseInt(camposFecha[1].substring(0, 2));
        GrupoClase grupoNew = new GrupoClase("Practica " + asignatura, grupoID, Integer.parseInt(camposFecha[1].substring(0, 2)), duracion, dayOfWeek, "P");
        return grupoNew;
    }

    public void imprimirMat(int[][] matriz) {
        int x = 0, y = 0;
        System.out.println(" " + "\t" + "M" + "\t" + "T" + "\t" + "W" + "\t" + "t" + "\t" + "F" + "\t");
        for (x = 0; x < h_fin - h_inicio; x++) { //FILAS=HORAS: el horario comienza la las h_inicio y termina a las h_fin
            System.out.println("");
            System.out.print(h_inicio + x + "\t");
            for (y = 0; y < 5; y++) {//COLUMNAS=DÍAS: los 5 días de la semana.
                System.out.print(matriz[x][y]);
                System.out.print("\t");
            }//fin for col
        }//fin for fil
        System.out.println();
    }//fin imprimirMat()

    public int sumarMat(int[][] matriz) {
        int x = 0, y = 0, suma = 0, unos = 0;
        for (x = 0; x < h_fin - h_inicio; x++) { //FILAS=HORAS: el horario comienza la las h_inicio y termina a las h_fin
            for (y = 0; y < 5; y++) {//COLUMNAS=D��AS: los 5 días de la semana.
                suma = suma + matriz[x][y];
                if (matriz[x][y] == 1) {
                    unos++;
                }
            }//fin for col
        }//fin for fil
        //System.out.println("\nLa suma de la matriz es=" + suma);
        //System.out.println("\nEl numero de unos es=" + unos);
        return suma;
    }//fin sumarMat()

    public int contarDif1(int[][] matriz) {
        int x = 0, y = 0, noUnos = 0;
        for (x = 0; x < h_fin - h_inicio; x++) { //FILAS=HORAS: el horario comienza la las h_inicio y termina a las h_fin
            for (y = 0; y < 5; y++) {//COLUMNAS=DÍAS: los 5 días de la semana.
                if (matriz[x][y] > 1) {
                    noUnos++;
                }
            }//fin for col
        }//fin for fil
        return noUnos;
    }//fin sumarMat()


    public int[] getRule() { //era getRule(int size)
        //TODO: nombre confuso pues no es un getter. Cambiar a generateGenRule()
        int cantGruposSize = getCantGrupos();
        int[] genRule = new int[cantGruposSize];
        int i = 0;
        //for (Map.Entry<Integer, Integer> entry : MateriasDeInteres.entrySet()) {
        //    Materia mat = Materias.get(entry.getValue());

        for (int j = 0; j < MateriasDeInteres.size(); j++) {
            Materia mat = Materias.get(MateriasDeInteres.get(j));
            int cantGrupos = mat.cantGrupos();
            int[] maximos = mat.maximos();
            for (int k = 0; k < cantGrupos; k++) {
                genRule[i] = maximos[k];
                i++;
            }
        }
        this.genRule = genRule;
        return genRule;
    }

    public int getCantGrupos() {
        int cantGrupos = 0;
 //       for (Map.Entry<Integer, Integer> entry : MateriasDeInteres.entrySet()) {
//            Materia mat = Materias.get(entry.getValue());
            for (int j = 0; j < MateriasDeInteres.size(); j++) {
                Materia mat = Materias.get(MateriasDeInteres.get(j));
            cantGrupos = cantGrupos + mat.cantGrupos();
        }
        this.cantGrupos = cantGrupos;
        return cantGrupos;
    }

    //public int[][] addHorario(int[] gen, int[][] matrizIndHorario){	 
    public int[][] addHorario(int[] gen, int[][] addHorario) {
        Boolean todoCeros = true;
        for (int i = 0; i < gen.length; i++) {
            if (gen[i] != 0) todoCeros = false;
        }
        if (!todoCeros) {
            GrupoClase grupoElegido = getGrupoDado(gen);
            Vector<Integer> HoraInicio = new Vector<Integer>(grupoElegido.getHoraInicio());
            Vector<Integer> Duracion = new Vector<Integer>(grupoElegido.getDuracion());
            Vector<Integer> Dia = new Vector<Integer>(grupoElegido.getDia());
            int horainicio = 0;
            int duracion = 0;
            int dia = 0;
            for (int i = 0; i < HoraInicio.size(); i++) {
                horainicio = HoraInicio.get(i);
                duracion = Duracion.get(i);
                dia = Dia.get(i);
                for (int j = 0; j < duracion; j++) {
                    addHorario[horainicio - h_inicio + j][dia - 1]++; //comprobar si dia empieza a contar en cero o uno, ver código en GrupoClase.java
                }
            }
        }
        return addHorario;

    }

    public GrupoClase getGrupoDado(int[] gen) {
        int count = 0;
        int grupo = 0;
        int control = 0;
        TreeMap<Integer, GrupoClase> grupos = new TreeMap<>();
        //ArrayList<GrupoClase> grupos = new ArrayList<>(10);
        boolean done = false;
        for (int i = 0; i < gen.length; i++) {
            if (gen[i] != 0) count = i; //puesto en el vector
        }
        grupo = gen[count]; //numero del grupo en cuestión
        materiasloop:
//        for (Map.Entry<Integer, Integer> entry : MateriasDeInteres.entrySet()) {
//            Materia mat = Materias.get(entry.getValue());

            for (int j = 0; j < MateriasDeInteres.size(); j++) {
                Materia mat = Materias.get(MateriasDeInteres.get(j));
            int cantGrupos = mat.cantGrupos();
            if ((control + cantGrupos) > count) {
                if (!done) {
                    int idGrupoClase = count - control;
                    grupos = mat.checkTipoGrupo(idGrupoClase);
                    //grupos = new TreeMap<GrupoClase>(mat.checkTipoGrupo(idGrupoClase));
                    done = true;
                }
            } else {
                control = control + cantGrupos;
            }
        }
        GrupoClase grupoElegido = grupos.get(grupo);
        if (grupoElegido == null) {
            if (grupo == 1) {
                grupoElegido = grupos.get("A");
            } else if (grupo == 2) {
                grupoElegido = grupos.get("B");
            } else if (grupo == 3) {
                grupoElegido = grupos.get("C");
            }
        }
        return grupoElegido;
    }

    public ArrayList<Sesion> exportGen(int[] gen) {
        ArrayList<Sesion> export = new ArrayList<>(100);
        for (int i = 0; i < gen.length; i++) {
            int[] grupoDado = new int[gen.length];
            for (int j = 0; j < grupoDado.length; j++) {
                grupoDado[j] = 0;
            }
            grupoDado[i] = gen[i];
            GrupoClase grupoClase = getGrupoDado(grupoDado);
            Vector<Integer> HoraInicio = grupoClase.getHoraInicio();
            Vector<Integer> Duracion = grupoClase.getDuracion();
            Vector<Integer> Dia = grupoClase.getDia();
            for (int j = 0; j < HoraInicio.size(); j++) {
                Sesion sesion = new Sesion();
                sesion.setNombre(grupoClase.getNombre());
                sesion.setDuracion(Duracion.get(j));
                sesion.setHora(HoraInicio.get(j));
                sesion.setTipoGrupo(grupoClase.getTipo());
                sesion.setDia(Dia.get(j));
                if(i%2==0){
                    if(gen[i]==1){
                        sesion.setAula("A");
                    }else if(gen[i]==2){
                        sesion.setAula("B");
                    }else if(gen[i]==3){
                        sesion.setAula("C");
                    }
                }else{
                    sesion.setAula(grupoClase.getId());
                }
                export.add(i, sesion);
            }
        }
        return export;
    }


    /*GETTERS Y SETTERS*/
    public int[][] getMat_disponibilidad() {
        return mat_disponibilidad;
    }

    public void setMat_disponibilidad(int[][] mat_disponibilidad) {
        this.mat_disponibilidad = mat_disponibilidad;
    }

    public int getCantGruposLowCPU() {
        return this.cantGrupos;
    }

}//fin class Horario

class Sesion implements Serializable, Comparable<Sesion> {

    private int hora;
    private int duracion;
    private int dia;
    private String aula;
    private String nombre;
    private String tipoGrupo;

    public Sesion() {
    }

    public Sesion(int hora, int duracion, int dia, String nombre, String tipo) {
        this.hora = hora;
        this.duracion = duracion;
        this.dia = dia;
        this.nombre = nombre;
        this.tipoGrupo = tipo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(String tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public int getDia() {
        return this.dia;
    }

    public void setDia(int dia) {
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

    @Override
    public int compareTo(Sesion sesion) {
        if (this.hora < sesion.hora) {
            return -1;
        }
        if (this.hora > sesion.hora) {
            return 1;
        }
        return 0;
    }
}



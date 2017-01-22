package com.psi.calendar;

public class IndividuoHorario {

	//private Horario horario;
	private int[] genoma = new int[size()]; //tiene que ser int
	private int[] genomaRule = new int[size()]; //tiene que ser int
	// Cache
	private int fitness = 0;

	/**
	 * Creates a new individual.To do so it creates a empty genome (all zeros) and gets the genomaRule. For doing
	 * this it should get the size of the genome from the function getCantGrupos() of Horario class.   
	 */
	public IndividuoHorario() {
		super();
		genoma = new int[size()];
		//genomaRule = new int[size()];
		this.genoma=genomaCeros(genoma);//TODO comprobar si esto genera un individuo con genoma inicializado a cero
		this.genomaRule=(Algorithm.getElHorario()).getRule();
	}


	/**
	 * Creates a random individual, a valid genome. It goes over the genoma, gene by gene giving them a valid random value.
	 * After each gene decision, it checks if the resulting timetable is acceptable or if, on the contrary,
	 * has overlapping classes.
	 */
	// Create a random individual
	public void generateIndividual() {
		boolean valido=false;
		int gene,maxTrys=10,trys=0; //TODO Trys: variable a cambiar con las demás que ajusten el algoritmo
		for (int i = 0; i < this.size(); i++) {//for(each gene in the genome)
			//TODO hacer que sea aleatorio el orden para asignar gen.
			while(!valido && trys<maxTrys){//while(we don't have a valid genome or we have tried to many times)
				//generate random gene
				gene = (int) Math.ceil(Math.random()*genomaRule[i]);// min=ceil(0.1*n)=1 /max=ceil(0.999*n)=n
				if (gene<=0) gene=1;
				genoma[i] = gene;
				//check gene
				valido=Algoritmo.checkAllGenoma(genoma);//comprobacion: valido=>next gene ; !valido=>repeat gene
				trys++;
			}
			trys=0;
			if(!valido){//we have tried to many times without success => start over
				i=-1;
				genoma=genomaCeros(genoma);
			}
			valido = false;
		}//for
	}


	/**
	 * Creates a genoma with zeros in all the positions except in the one indicated by the index parameter,
	 * which will have the value given by the xvalue parameter.
	 * @param x
	 * The index where the gene it is gona be different form zero.
	 * @param xvalue
	 * The value of the gene in the x position.
	 * @param size
	 * The size of the genoma, the number of genes it will have, the number of classes in the timetable.
	 * @return
	 * A genoma almost empty, with only one gene decided.
	 */
	public int[] genomaWithCeros(int x, int xvalue, int size){
		int[] newGen= new int[size];
		for(int i=0;i<size;i++){
			if(i==x){ newGen[i]=xvalue; }
			else{ newGen[i]=0; }
		}
		return newGen;
	}
	/* Getters and setters */
	// Use this if you want to create individuals with different gene lengths
	/*   public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
	 */
	/**
	 * Gets the genoma of the individual.
	 * @return
	 * The genoma of the individual.
	 */
	public int[] getGenoma() {
		return genoma;
	}

	/**
	 * Sets the genoma of the individual.
	 * @param genoma
	 * The genoma we want to put in the individual.
	 */
	public void setGenoma(int[] genoma) {
		this.genoma = genoma;
		fitness = 0;//si??
	}

	/**
	 * Gets the gene that is in the genome in the position indicated by the index parameter.
	 * @param index
	 * The position of the gene in the genome.
	 * @return
	 * The gene we want of the genome of the individual.
	 */
	public int getGene(int index) {
		return genoma[index];
	}

	/**
	 * Sets the gene that is in the genome in the position indicated by the index parameter to the value
	 * indicated by the value parameter.
	 * @param index
	 * The position of the gene in the genome.
	 * @param value
	 * The value we want the gene to have.
	 */
	public void setGene(int index, int value) {
		genoma[index] = value;
		fitness = 0;
	}

	/**
	 * Gets the size that the genome should have, the number of classes we have in the timetable.
	 * @return
	 * The size of the genome.
	 */
	public int size() {
		//int longGen=(Principal.getElHorario()).getCantGrupos();
		int longGen = Algorithm.getElHorario().getCantGruposLowCPU();
		return longGen;
		//return genes.length; // se saca del numero de materias de de genes.length.
	}

	/**
	 * Estimates how good is an individual, the timetable.
	 * @return
	 * The punctuation of the individual.
	 */
	public int getFitness() {
		if (fitness == 0) {
			fitness = CalculoFitness.getFitness(this);
		}
		return fitness;
	}

	/**
	 * Returns a genome with all the genes with zero value.
	 * @param theGenoma
	 * The genoma of which we want to set all genes to zero.
	 * @return
	 * An all zero genoma.
	 */
	public static int[] genomaCeros(int[] theGenoma) {
		for(int i=0;i<theGenoma.length;i++){
			theGenoma[i]=0;
		}
		return theGenoma;
	}


	@Override
	public String toString() {
		String geneString = "";
		for (int i = 0; i < size(); i++) {
			geneString += getGene(i);
		}
		return geneString;
	}
}

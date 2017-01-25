package com.psi.calendar;

import java.util.Random;

public class Algoritmo {

	/* GA parameters */
	private static final double uniformRate = 0.5;//TODO valorar que desde la aplicación se pueda ajustar
	private static final double mutationRate = 0.025;
	private static final int tournamentSize = 5; //
	private static final boolean elitism = true;

	/* Public methods */

	/**
	 * Evolves a population = [save the best] + crossover + mutation. The parameter pop is out population
	 * (a group of individuals, which is a group of possible timetables). 
	 * @param pop
	 * The population we want to evolve.
	 * @return 
	 * A new population, the evolution of the population pop.
	 */ 
	public static PoblacionHorarios evolvePopulation(PoblacionHorarios pop) {
		PoblacionHorarios newPopulation = new PoblacionHorarios(pop.tamano(), false);

		// Keep our best individual
		if (elitism) {
			newPopulation.saveIndividual(0, pop.getFittest());//TODO cambiar si queremos guardar más de uno
		}
		// Crossover population
		int elitismOffset;
		if (elitism) {
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}
		// Loop over the population size and create new individuals with crossover
		for (int i = elitismOffset; i < pop.tamano(); i++) {
			IndividuoHorario indiv1 = tournamentSelection(pop);
			IndividuoHorario indiv2 = tournamentSelection(pop);
			IndividuoHorario newIndiv = crossover(indiv1, indiv2);
			newPopulation.saveIndividual(i, newIndiv);
		}

		// Mutate the new population: the population obtained after apply crossover to the old population ("pop").
		for (int i = elitismOffset; i < newPopulation.tamano(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	
	/**
	 * Select individuals for crossover. Select the best individual of a random group of individuals of 
	 * our population. The variable tournamentSize decides the size of the group of individuals.
	 * @param pop
	 * The population from which we are going to select one individual.
	 * @return
	 * The best individual
	 */
	private static IndividuoHorario tournamentSelection(PoblacionHorarios pop) {
		// Create a tournament population
		PoblacionHorarios tournament = new PoblacionHorarios(tournamentSize, false);
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++) {
			Random rand = new Random();
			int randomId = rand.nextInt(pop.tamano()); //int randomId = (int) (Math.random() * pop.tamano());
			tournament.saveIndividual(i, pop.getIndividual(randomId));
		}
		// Get the fittest
		IndividuoHorario fittest = tournament.getFittest();
		return fittest;
	}
	
	
	/**
	 * Crossover individuals. Creates a new individual with his new genome by choosing gene by gene
	 * randomly between the genes of individual1 and individual2. 
	 * @param indiv1
	 * @param indiv2
	 * @return
	 * A new individual which is a mixture from the both individuals given as parameters.
	 */
	private static IndividuoHorario crossover(IndividuoHorario indiv1, IndividuoHorario indiv2) {

		//int[][] horarioGen; 
		//int[] genomaRule;//particular rules that a genome must fulfill (depending on the number of groups available for choosing.	
		int numIntentos=0;
		boolean found;//we have found a partial genome valid, we are heading in the right direction
		boolean exito=false;//we have found a complete genome valid, we have a new individual
		IndividuoHorario newSol = new IndividuoHorario();//now it has a genome with all zeros
		//Horario obHorario=new Horario();

		//horarioGen=(Principal.getElHorario()).crearMat_vacia();//TODO comprobar que esto está bien. Sino lo está buscar esto en más partes del código
		//genomaRule=(Principal.getElHorario()).getRule();

		// Loop through genes
		do{//while(!exito && numIntentos<15) 
			//If we don't success after several attempts we will pick up randomly one of the individuals to cross.
			//TODO: puede que fuese mejor tb randomizar la posición a escoger por la que empezar...O mejor no
			found=false;
			for (int i = 0; i < indiv1.size(); i++) {//for each gene i of the new genoma...
				// Crossover
				boolean probeInd1;
				//We try with one of them...
				if (Math.random() <= uniformRate) {//... we choose randomly the i gene of one of the two individuals.
					//TODO puede ser necesario para encontrar un crossover valido cambiarlo
					newSol.setGene(i, indiv1.getGene(i));
					probeInd1=true;
				} else {
					newSol.setGene(i, indiv2.getGene(i));
					probeInd1=false;
				}
				found=checkAllGenoma(newSol.getGenoma());//checking
				//... If that isn't valid we try with the other one.
				if(!found){
					if (!probeInd1) {
						newSol.setGene(i, indiv1.getGene(i));
					} else {
						newSol.setGene(i, indiv2.getGene(i));
					}
					found=checkAllGenoma(newSol.getGenoma());//checking
				}
				//If none of them was valid we try another time form the beginning
				if(!found){
					numIntentos++;
					break;//We start over from the beginning, from the first gene of the genome.
				}//TODO comprobar que se sale del for ese break
			}//for(each gene)
			if(found){//if when we leave the for found is trues means we have been successful, we have a complete valid genome.
				exito=true;
			}
		}while(!exito && numIntentos<15);//TODO revisar esta lógica
		if(numIntentos==15){//If after all we haven't been successful, we choose randomly one individual from the parameters given.
			if (Math.random() <= uniformRate) {
				newSol.setGenoma(indiv1.getGenoma());
			} else {
				newSol.setGenoma(indiv2.getGenoma());
			}
		}
		return newSol;//devuelve un individuo
	}


	/**
	 * Mutate an individual. It gets an individual and with a low probability mutates some of the genes in
	 * his genome if it is possible.
	 * @param indiv
	 * The individual we are going to mutate
	 */
	private static void mutate(IndividuoHorario indiv) {
//		int[][] horarioGen; 
		int[] genRule;	
		int numIntentos=0;
		boolean found=false;
		Horario obHorario=new Horario();//TODO y si no declaro horario y crearMat_vacia es static?
		IndividuoHorario mutantIndiv = new IndividuoHorario();

		mutantIndiv.setGenoma(indiv.getGenoma());//we give the mutant individual the genome of the original invidivual 
//		horarioGen=obHorario.crearMat_vacia();//T ODO aqui y en crossover ver si crear funcion para gen rule a partir de indiv o pasar por parametros
		genRule=(Algorithm.getElHorario()).getRule();

		// Loop through genes
		for (int i = 0; i < indiv.size(); i++) {//for(each gene in the genome)
			do{//while(!found && numIntentos<10)
				if (Math.random() <= mutationRate) {
					// Create random gene
					mutantIndiv.setGene(i, (int) Math.ceil(Math.random()*genRule[i]));//TODO comprobar
					if(mutantIndiv.getGene(i)<=0){
						mutantIndiv.setGene(i, 1);
					}
					//horarioGen=obHorario.addHorario(newIndivMut.getGenoma(), horarioGen);//T ODO tengo que hacerlo incremental. Ya se hace incremental pero no se inicia a 0 los genes al crear el individuo.
					//T ODO cambiar como en getFiness de calculoFitness... podría crear una funcion que sea genHorario

					found=checkAllGenoma(mutantIndiv.getGenoma());//Check
					//found=checkNewGen(newSol.getGene(),horarioGen,i);	
					numIntentos++;
					if(found){numIntentos=0;}
				}//if(mutate)
			}while(!found && numIntentos<10);
			if(!found){//if we don't find an apropiate mutation...
				mutantIndiv.setGene(i, indiv.getGene(i));//... we do not mutate this gene
			}
		}//for(each gene in the genome)
	}
	

	/**
	 * Checks with the genoma if the individuo is valid, if the timetable doesn't overlap.
	 * @param genoma
	 * The genoma that needs to be checked.
	 * @return valido
	 * True if the genoma is valid or false if not.
	 */
	public static boolean checkAllGenoma(int[] genoma){
		int matrizComprobacion[][];
		int[] genoma1gen= new int[genoma.length];

		matrizComprobacion=Algorithm.getElHorario().crearMat_vacia();//we created a clear timetable
		for(int i=0;i<genoma.length;i++){
			genoma1gen=only1gen(genoma,i);
			matrizComprobacion=(Algorithm.getElHorario()).addHorario(genoma1gen, matrizComprobacion); 
			//TODO comprobar que addHorario no modifica al objeto horario con el que se le llama
		}
		//TODO comprobar que contarDif1 no modifica al objeto horario con el que se le llama
		//Principal.getElHorario().imprimirMat(matrizComprobacion);
		if((Algorithm.getElHorario()).contarDif1(matrizComprobacion)==0){
			return true;
		}else return false;
	}

	
	/**
	 * 
	 * @param genoma
	 * @param posicion
	 * @return
	 */
	public static int[] only1gen (int[] genoma, int posicion){
		int[] genoma1gen= new int[genoma.length];
		for(int i=0;i<genoma.length;i++ ){
			if(i==posicion){
				genoma1gen[i]=genoma[i];
			}else{
				genoma1gen[i]=0;
			}
		}
		return genoma1gen;
	}
	
	
}



/*
public static boolean checkNewGen(int[] gen,int[][] horarioGen,Horario obHorario){//T ODO revisar
	horarioGen=obHorario.addHorario(gen, horarioGen);
	//T ODO cambiar como en getFiness de calculoFitness... podr��a crear una funcion que sea genHorario
	if(obHorario.contarDif1(horarioGen)==0){
		return true;
	}else return false;
}
 */	

/*	public static boolean checkNewGen(int[] genoma,int[][] horarioGen,int i){//T ODO revisar
	int[] genoma1gen=new int[genoma.length];
	//We add to horarioGen all new classes which will be indicated by gen i in genoma
	genoma1gen=only1gen(genoma,i);
	horarioGen=(Principal.getElHorario()).addHorario(genoma1gen, horarioGen);

	//T ODO cambiar como en getFiness de calculoFitness... podría crear una funcion que sea genHorario
	if((Principal.getElHorario()).contarDif1(horarioGen)==0){
		return true;
	}else return false;
}
 */






//PROBAMOS CON UNA COMBINACION NUEVA
//T ODO esto es en mutation
/*
	newSol.setGeneI(i, (int) Math.round(Math.random()*genRule[i]));
	//comprobacion
	horarioGen=obHorario.addHorario(newSol.getGene(), horarioGen);
	//T ODO cambiar como en getFiness de calculoFitness... podría crear una funcion que sea genHorario
	found=esValido(newSol.getGene(),horarioGen,obHorario);	
	numIntentos++;
 */
//}while(!found && numIntentos<5);

/*
if(!found){
	//T ODO Podríamos hacer mutación si no sale o volver a empezar. Ahora mismo volvemos a empezar.
}//if(!found)

 */







//found=checkNewGen(newSol.getGene(),horarioGen,i);//comprobacion


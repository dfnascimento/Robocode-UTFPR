/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roborescue.testes;

import roborescue.astar.AStarImpl;
import roborescue.astar.Action;
import roborescue.astar.Position;

/**
 *
 * @author guilherme
 */
public class TesteHeuristicas {

    public static void main(String[] args) {
        AStarImpl alg = new AStarImpl(new Position(2406.0, 750.0), null);
        try {
            double h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.LESTE);
            System.out.println("LESTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.NORDESTE);
            System.out.println("NORDESTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.SUDESTE);
            System.out.println("SUDESTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.NORTE);
            System.out.println("NORTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.SUL);
            System.out.println("SUL: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.SUDOESTE);
            System.out.println("SUDOESTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.NOROESTE);
            System.out.println("NOROESTE: " + h);

            h = alg.getCustoEstimado(new Position(200.0, 600.0), Action.OESTE);
            System.out.println("OESTE: " + h);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

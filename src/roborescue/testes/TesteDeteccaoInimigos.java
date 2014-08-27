/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roborescue.testes;

import java.util.ArrayList;
import java.util.List;

import roborescue.astar.Action;
import roborescue.astar.Position;
import roborescue.tarefabusca.AcoesProibidas;

/**
 *
 * @author guilherme
 */
public class TesteDeteccaoInimigos {

    public static void main(String[] args) {
        List<Position> listaInimigos = new ArrayList<>();

        Position rescuer = new Position(600.0, 200.0);
        Position sudeste = new Position(750.0, 58.0);
        Position norte = new Position(600.0, 280.0);
        Position oeste = new Position(520.0, 200.0);
        
        Position lesteNordeste = new Position(750.0, 125.0);
        
        listaInimigos.add(oeste);
        listaInimigos.add(norte);
        listaInimigos.add(sudeste);
        listaInimigos.add(lesteNordeste);
//        
//        Position norteNordeste = new Position(650.0, 350.0);
//        Position sulSudeste = new Position(650.0, 50.0);
//        Position norteNoroeste = new Position(550.0, 350.0);
//        Position sulSudoeste = new Position(550.0, 50.0);
//        
//        listaInimigos.add(norteNordeste);
//        listaInimigos.add(sulSudeste);
//        listaInimigos.add(norteNoroeste);
//        listaInimigos.add(sulSudoeste);
        
//        Position norte = new Position(600.0, 360.0);
//        Position sul = new Position(600.0, 50.0);
//        Position leste = new Position(750.0, 200.0);
//        Position oeste = new Position(450.0, 200.0);
//        
//        listaInimigos.add(norte);
//        listaInimigos.add(sul);
//        listaInimigos.add(leste);
//        listaInimigos.add(oeste);
        
        AcoesProibidas utils = null;
		try {
			utils = new AcoesProibidas(Action.LESTE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        List<Action> acoesProibidas = utils.getAcoesProibidas(rescuer, listaInimigos);

        for (Action a : acoesProibidas) {
            System.out.println(a);
        }
    }
}

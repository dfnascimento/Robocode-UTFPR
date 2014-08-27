/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roborescue.tarefabusca;

import java.rmi.RemoteException;
import robocode.rescue.interfaces.RMIRobotInterface;
import roborescue.astar.Action;

/**
 *
 * @author guilherme
 */
public class Movimento {

    /**
     * Custo de uma célula para outra horizontal ou vertical. Em Russel e Norvig
     * é a função de custo <b>g(n)</b>.
     */
    private final double TAMANHO_CELULA = 120.0;

    /**
     * Custo de uma célula para outra diagonal. Em Russel e Norvig é a função de
     * custo <b>g(n)</b>.
     */
    private final double TAMANHO_DIAGONAL_CELULA = Math.sqrt(Math.pow(TAMANHO_CELULA, 2) + Math.pow(TAMANHO_CELULA, 2));

    /**
     * Recebe uma ação e a executa no robô.
     *
     * @param rescuer
     * @param acaoPretendida
     * @param ultimaAcao
     * @throws RemoteException
     */
    public void mover(RMIRobotInterface rescuer, Action acaoPretendida,
            Action ultimaAcao) throws RemoteException {
        switch (acaoPretendida) {
            case NORTE:
                moverNorte(rescuer, ultimaAcao);
                break;
            case SUL:
                moverSul(rescuer, ultimaAcao);
                break;
            case OESTE:
                moverOeste(rescuer, ultimaAcao);
                break;
            case LESTE:
                moverLeste(rescuer, ultimaAcao);
                break;
            case NORDESTE:
                moverNordeste(rescuer, ultimaAcao);
                break;
            case SUDESTE:
                moverSudeste(rescuer, ultimaAcao);
                break;
            case NOROESTE:
                moverNoroeste(rescuer, ultimaAcao);
                break;
            case SUDOESTE:
                moverSudoeste(rescuer, ultimaAcao);
                break;
        }
    }

    /**
     * Move de qualquer posição para o norte.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverNorte(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        /*switch (ultimaAcao) {
            case NORTE:
                // Não faz nada, já está posicionado
                break;
            case NORDESTE:
                rescuer.setTurnLeft(45);
                break;
            case LESTE:
                rescuer.setTurnLeft(90);
                break;
            case SUDESTE:
                rescuer.setTurnLeft(135);
                break;
            case SUL:
                rescuer.setTurnLeft(180);
                break;
            case SUDOESTE:
                rescuer.setTurnRight(135);
                break;
            case OESTE:
                rescuer.setTurnRight(90);
                break;
            case NOROESTE:
                rescuer.setTurnRight(45);
                break;
        }*/
    	
        if (rescuer.getRobotInfo().getHeading() > 180) {
            Double degree = (0 - rescuer.getRobotInfo().getHeading() + 360) % 360;

            rescuer.turnRight(degree);
        } else if (rescuer.getRobotInfo().getHeading() != 0) {
            Double degree = (rescuer.getRobotInfo().getHeading() + 360) % 360;

            rescuer.turnLeft(degree);
        }


        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_CELULA);
        rescuer.execute();
    }

    /**
     * Move de qualquer posição para o sul.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverSul(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnRight(180);
                break;
            case NORDESTE:
                rescuer.setTurnRight(135);
                break;
            case LESTE:
                rescuer.setTurnRight(90);
                break;
            case SUDESTE:
                rescuer.setTurnRight(45);
                break;
            case SUL:
                // Não faz nada, já está no sul
                break;
            case SUDOESTE:
                rescuer.setTurnLeft(45);
                break;
            case OESTE:
                rescuer.setTurnLeft(90);
                break;
            case NOROESTE:
                rescuer.setTurnLeft(135);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_CELULA);
        rescuer.execute();
    }

    /**
     * Move de qualquer posição para o sul.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverOeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnLeft(90);
                break;
            case NORDESTE:
                rescuer.setTurnLeft(135);
                break;
            case LESTE:
                rescuer.setTurnLeft(180);
                break;
            case SUDESTE:
                rescuer.setTurnRight(135);
                break;
            case SUL:
                rescuer.setTurnRight(90);
                break;
            case SUDOESTE:
                rescuer.setTurnRight(45);
                break;
            case OESTE:
                // Não faz nada, já está no oeste
                break;
            case NOROESTE:
                rescuer.setTurnLeft(45);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_CELULA);
        rescuer.execute();
    }
    
    /**
     * Move de qualquer posição para o leste.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverLeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnRight(90);
                break;
            case NORDESTE:
                rescuer.setTurnRight(45);
                break;
            case LESTE:
                // Já está no Leste, não faz nada
                break;
            case SUDESTE:
                rescuer.setTurnLeft(45);
                break;
            case SUL:
                rescuer.setTurnLeft(90);
                break;
            case SUDOESTE:
                rescuer.setTurnLeft(135);
                break;
            case OESTE:
                rescuer.setTurnRight(180);
                break;
            case NOROESTE:
                rescuer.setTurnRight(135);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_CELULA);
        rescuer.execute();
    }
    
    /**
     * Move de qualquer posição para o nordeste.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverNordeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnRight(45);
                break;
            case NORDESTE:
                // Não faz nada, já está no nordeste
                break;
            case LESTE:
                rescuer.setTurnLeft(45);
                break;
            case SUDESTE:
                rescuer.setTurnLeft(90);
                break;
            case SUL:
                rescuer.setTurnLeft(135);
                break;
            case SUDOESTE:
                rescuer.setTurnLeft(180);
                break;
            case OESTE:
                rescuer.setTurnRight(135);
                break;
            case NOROESTE:
                rescuer.setTurnRight(90);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_DIAGONAL_CELULA);
        rescuer.execute();
    }

    /**
     * Move de qualquer posição para o sudeste.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverSudeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnRight(135);
                break;
            case NORDESTE:
                rescuer.setTurnRight(90);
                break;
            case LESTE:
                rescuer.setTurnRight(45);
                break;
            case SUDESTE:
                // Não faz nada, já está no sudeste
                break;
            case SUL:
                rescuer.setTurnLeft(45);
                break;
            case SUDOESTE:
                rescuer.setTurnLeft(90);
                break;
            case OESTE:
                rescuer.setTurnLeft(135);
                break;
            case NOROESTE:
                rescuer.setTurnLeft(180);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_DIAGONAL_CELULA);
        rescuer.execute();
    }

    /**
     * Move de qualquer posição para o sudoeste.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverSudoeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnLeft(135);
                break;
            case NORDESTE:
                rescuer.setTurnLeft(180);
                break;
            case LESTE:
                rescuer.setTurnRight(135);
                break;
            case SUDESTE:
                rescuer.setTurnRight(90);
                break;
            case SUL:
                rescuer.setTurnRight(45);
                break;
            case SUDOESTE:
                // Não faz nada, já está no sudoeste
                break;
            case OESTE:
                rescuer.setTurnLeft(45);
                break;
            case NOROESTE:
                rescuer.setTurnLeft(90);
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_DIAGONAL_CELULA);
        rescuer.execute();
    }
    
    /**
     * Move de qualquer posição para o sul.
     *
     * @param rescuer
     * @throws RemoteException
     */
    private void moverNoroeste(RMIRobotInterface rescuer, Action ultimaAcao)
            throws RemoteException {
        // Faz posicionamento
        switch (ultimaAcao) {
            case NORTE:
                rescuer.setTurnLeft(45);
                break;
            case NORDESTE:
                rescuer.setTurnLeft(90);
                break;
            case LESTE:
                rescuer.setTurnLeft(135);
                break;
            case SUDESTE:
                rescuer.setTurnRight(180);
                break;
            case SUL:
                rescuer.setTurnRight(135);
                break;
            case SUDOESTE:
                rescuer.setTurnRight(90);
                break;
            case OESTE:
                rescuer.setTurnRight(45);
                break;
            case NOROESTE:
                // Não faz nada, já está no noroeste
                break;
        }

        // Anda em em frente e executa a ação
        rescuer.ahead(TAMANHO_DIAGONAL_CELULA);
        rescuer.execute();
    }

}

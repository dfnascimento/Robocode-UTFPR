/* Este programa eh modelo para ser utilizado na tarefa de busca A* x LRTA*
 da disciplina de Sistemas Inteligentes 1.
 O time A contem o robo refem (que serah buscado) e o robo salvador (que
 efetua a busca).
 */
package roborescue.tarefabusca;

import jason.RoborescueEnv;
import jason.asSyntax.Structure;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import robocode.rescue.RobotInfo;
import robocode.rescue.interfaces.RMIRobotInterface;
import roborescue.astar.AStarImpl;
import roborescue.astar.Action;
import roborescue.astar.Position;

public class TimeAEnv extends RoborescueEnv {

    private final int robotQty = 5;
    private RobotInfo[] myRobots;        // usado para obter informacoes dos robos
    private RobotInfo[] enemyRobots;     // usado para obter informacoes dos robos
    private RMIRobotInterface[] teamRef; // usado para enviar acoes aos robos
    private RobotInfo hostage;  // robo refem a ser resgatado 
    private RobotInfo rescuer;  // robo que efetua o resgate

    /**
     * Ultima ação tomada pelo robô.
     */
    private Action ultimaAcao;

    /**
     * Algoritmo A estrela.
     */
    private AStarImpl aEstrela;

    /**
     * Lista de inimigos e suas posições.
     */
    private List<Position> inimigos;

    /**
     * Custo de uma célula para outra horizontal ou vertical. Em Russel e Norvig
     * é a função de custo <b>g(n)</b>.
     */
    private final double TAMANHO_CELULA = 140.0;

    /**
     * Custo de uma célula para outra diagonal. Em Russel e Norvig é a função de
     * custo <b>g(n)</b>.
     */
    private final double TAMANHO_DIAGONAL_CELULA = Math.sqrt(Math.pow(TAMANHO_CELULA, 2) + Math.pow(TAMANHO_CELULA, 2));

    double xRefem;
    double yRefem;

    /**
     * Movimento do robo
     */
    private Movimento movimento;
    
    /**
     * Direção da base do time 
     */
    private Action base;
    
    //Para inicializacoes necessarias
    @Override
    public void setup() {
        // NAO FIZ INICIALIZACOES AQUI PORQUE ESTAH SENDO CHAMADO 2 VEZES   
        // CRIEI METODO setInitialPosition
    }

    void setInitialPosition() {
        movimento = new Movimento();
        
        try {
            System.out.println("*** TAMANHO DO CAMPO ***");
            System.out.println("Battle field Width  = " + getServerRef().getBattlefieldWidth());
            System.out.println("Battle field Height = " + getServerRef().getBattlefieldHeight());

            System.out.println("*** SETUP " + myTeam + " ***");
            myRobots = new RobotInfo[robotQty];
            myRobots = getServerRef().getMyTeamInfo(myTeam);

            // O robo[0] do time A eh o refem e inicia posicionado na 
            // extremidade oposta do campo em relacao aos seus companheiros.
            // O refem (hostage) eh o robo que sera buscado nesta tarefa
            // Nao mude a posicao do refem - dah pau
            hostage = myRobots[0];
            xRefem = hostage.getX();
            ;yRefem = hostage.getY();
            System.out.println(myTeam + ": pos robo a ser buscado X="
                    + xRefem + " Y=" + yRefem);

            // o robo[1] do time A eh o salvador que deve buscar o refem
            // vc pode escolher entre mexer ou nao nao posicao inicial dele
            rescuer = myRobots[1];
            rescuer.setVelocity(1000);
            
            double xRescuer = rescuer.getX();
            double yRescuer = rescuer.getY();
            System.out.println(myTeam + ": pos robo salvador  X="
                    + xRescuer + " Y=" + yRescuer);

            // TO DO - posicionar os robos aleatoriamente para o A* ou em
            // em posicoes fixas quando for testar sucessivas vezes
            // Abaixo, exemplo de posicionamento fixo
            teamRef = getTeamRef();

            // Não mexi nos demais robos do time A
            // teamRef[2].setTurnLeft(45);
            // teamRef[2].setAhead(500);
            // teamRef[2].execute();
            //            
            // teamRef[3].setTurnRight(10);
            // teamRef[3].setAhead(1000);
            // teamRef[3].execute();
            //            
            // teamRef[4].setTurnRight(-45);
            // teamRef[4].setAhead(500);
            // teamRef[4].execute();
            // Obtem informacoes dos robos do time inimigo - as posicoes dos 
            // robos inimigos devem ser fixadas no TimeBEnv - o que eh
            // importante para executar os algoritmos de busca
            enemyRobots = new RobotInfo[robotQty];
            // observar que o arg eh o proprio nome do time para impedir trapacas
            enemyRobots = getServerRef().getEnemyTeamInfo(myTeam);
            ultimaAcao = Action.LESTE;
            inimigos = new ArrayList<Position>();

            System.out.println("*** Inimigos de " + myTeam + " ***");
            for (int i = 0; i < robotQty; i++) {
                System.out.println(" [" + i + "]: X=" + enemyRobots[i].getX()
                        + " Y=" + enemyRobots[i].getY());
                Position posicaoInimigo = new Position(enemyRobots[i].getX(), enemyRobots[i].getY());
                inimigos.add(posicaoInimigo);
            }
            
            aEstrela = new AStarImpl(new Position(xRefem, yRefem), inimigos);
            
        } catch (RemoteException ex) {
            System.out.println("Falha ao pegar server reference - setup " + myTeam);
        }
    }

    @Override
    public boolean executeAction(String ag, Structure action) {

        try {
            mainLoop();
            Thread.sleep(20);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return true;

    }
    /* MainLoop 
     - chamado a cada turno ou ciclo de execucao
     */

    public void mainLoop() throws RemoteException {
        // TO DO: o codigo abaixo eh soh tampao. O que vai colocar ai depende do 
        // do algoritmo, se A* ou LRTA*, e faz parte da tarefa

        System.out.println("*** ROBOS do " + myTeam + " ***");
        // Para pegar a pos atualizada eh necessario invocar o metodo
        // getMyTeamInfo
        myRobots = getServerRef().getMyTeamInfo(myTeam);

        // posicoes x, y dos  robos do time A
        for (int i = 0; i < robotQty; i++) {
            System.out.println(" [" + i + "]: X=" + myRobots[i].getX()
                    + " Y=" + myRobots[i].getY());
        }

        enemyRobots = getServerRef().getEnemyTeamInfo(myTeam);

        List<Position> inimigosUpdate = new ArrayList<>();
        
        System.out.println("*** Robos inimigos de " + myTeam + " ***");
        for (int i = 0; i < robotQty; i++) {
            System.out.println(" [" + i + "]: X=" + enemyRobots[i].getX()
                    + " Y=" + enemyRobots[i].getY());
            Position posicaoInimigo = new Position(enemyRobots[i].getX(), enemyRobots[i].getY());
            inimigosUpdate.add(posicaoInimigo);
        }
        
        aEstrela.setInimigos(inimigosUpdate);

        Action acaoPretendida = aEstrela.move(new Position(myRobots[1].getX(), myRobots[1].getY()));
        
        movimento.mover(teamRef[1], acaoPretendida, ultimaAcao);
        
        System.out.println(aEstrela.getCustoTotal());
        ultimaAcao = acaoPretendida;
    }

    @Override
    public void end() {
        try {
            super.getEnvironmentInfraTier().getRuntimeServices().stopMAS();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        TimeAEnv team = new TimeAEnv();
        team.init(new String[]{"Power Rangers", "localhost"});
        team.setup();
        team.setInitialPosition();
        while (true) {
            try {
                team.mainLoop();
                Thread.sleep(20);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}

/* Este programa eh modelo para ser utilizado na tarefa de busca A* x LRTA*
   da disciplina de Sistemas Inteligentes 1.
   O time B contem robos que deverao ser posicionados no campo (eles nao
   se mexem apos este posicionamento inicial)
 */

package roborescue.tarefabusca;

import jason.RoborescueEnv;
import jason.asSyntax.Structure;

import java.rmi.RemoteException;

import robocode.rescue.RobotInfo;
import robocode.rescue.interfaces.RMIRobotInterface;

public class TimeBEnv extends RoborescueEnv {
	private final int robotQty = 5;
	private RobotInfo[] myRobots;
	private RobotInfo[] enemyRobots;
	private RMIRobotInterface[] teamRef;
	private RMIRobotInterface[] enemyRef;
	private RobotInfo hostage; // robo a ser resgatado
	private RobotInfo rescuer; // robo que efetua o resgate

	// Para inicializacoes necessarias
	@Override
	public void setup() {
		// NAO FIZ INICIALIZACOES AQUI PORQUE ESTAH SENDO CHAMADO 2 VEZES
		// CRIEI METODO setInitialPosition
	}

	void setInitialPosition() {
		try {
			System.out.println("*** TAMANHO DO CAMPO ***");
			System.out.println("Battle field Width  = "
					+ getServerRef().getBattlefieldWidth());
			System.out.println("Battle field Height = "
					+ getServerRef().getBattlefieldHeight());

			System.out.println("*** SETUP " + myTeam + " ***");

			// para obter informacoes do robo deste time
			myRobots = new RobotInfo[robotQty];
			myRobots = getServerRef().getMyTeamInfo(myTeam);

			// TO DO - o codigo que segue eh apenas um exemplo de posicionamento
			// fixo dos robos do time B. Alterar para fazer posicionamento
			// aleatorio (A*) e fixar para as execucoes sucessivas do LRTA*

			// posiciona robos deste time em posicoes arbitrarias
			teamRef = getTeamRef();

			// !!! nao movimentar o robo[0] - DA PAU !!!
			// teamRef[0].setAhead(2000);
			// teamRef[0].execute();

			teamRef[1].setTurnLeft(30);
			teamRef[1].setAhead(500);
			teamRef[1].execute();

			teamRef[2].setAhead(100);
			teamRef[2].setTurnLeft(20);
			teamRef[2].setAhead(800);
			teamRef[2].execute();

			teamRef[3].setTurnLeft(10);
			teamRef[3].setAhead(700);
			teamRef[3].execute();

			teamRef[4].setAhead(900);
			teamRef[4].execute();

		} catch (RemoteException ex) {
			System.out.println("Falha ao pegar server reference - setup "
					+ myTeam);
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

	public void mainLoop() throws RemoteException {

		System.out.println("*** ROBOS do " + myTeam + " ***");
		myRobots = getServerRef().getMyTeamInfo(myTeam);

		// posicoes x, y dos demais robos deste time
		for (int i = 0; i < robotQty; i++) {
			System.out.println(" [" + i + "]: X=" + myRobots[i].getX() + " Y="
					+ myRobots[i].getY());

		}

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
		TimeBEnv team = new TimeBEnv();
		team.init(new String[] { "TimeB", "localhost" });
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

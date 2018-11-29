import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculadora extends JFrame {
	private JTextField display;
	private JButton boton[] = new JButton[16];
	private JPanel panelBotones;
	private String n = "0";
	private char prevOperator = ' ';
	private char currentOperator = '=';
	private int resultado = 0;

	Calculadora() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		display = new JTextField("0");
		add(display, BorderLayout.NORTH);

		panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(4, 4));
		add(panelBotones, BorderLayout.CENTER);

		boton[0] = new JButton("7");
		boton[1] = new JButton("8");
		boton[2] = new JButton("9");
		boton[3] = new JButton("+");
		boton[4] = new JButton("4");
		boton[5] = new JButton("5");
		boton[6] = new JButton("6");
		boton[7] = new JButton("-");
		boton[8] = new JButton("1");
		boton[9] = new JButton("2");
		boton[10] = new JButton("3");
		boton[11] = new JButton("*");
		boton[12] = new JButton("C");
		boton[13] = new JButton("0");
		boton[14] = new JButton("=");
		boton[15] = new JButton("/");

		ActionListener numberListener = new NumberListener();
		ActionListener operatorListener = new OperatorListener();

		for (JButton b : boton) {
			if (isInteger(b.getText())) {
				b.addActionListener(numberListener);
			} else if (b.getText().equals("C")) {
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						resultado = 0;
						prevOperator = ' ';
						n = "0";
						display.setText("0");
					}
				});
			} else {
				b.addActionListener(operatorListener);
			}
			panelBotones.add(b);
		}


		setTitle("Calculadora");
		setSize(300, 400);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class NumberListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (n.equals("0")) {
				n = "";
			}

			n += evt.getActionCommand();
			display.setText(n);
		}
	}

	private class OperatorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			prevOperator = currentOperator;
			currentOperator = evt.getActionCommand().charAt(0);
			if (resultado == 0) {
				resultado = operar(Integer.valueOf(display.getText()), '=', resultado);

			} else if ((prevOperator == '*' || prevOperator == '/') && resultado !=0) {
				resultado = operar(Integer.valueOf(display.getText()), prevOperator, resultado);
				display.setText(resultado + "");
			} else {
				resultado = operar(Integer.valueOf(display.getText()), prevOperator, resultado);
				display.setText(resultado + "");
			}
			n = "0";

		}
	}

	public boolean isInteger(String x) {
		try {
			Integer.valueOf(x);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int operar(int x, char operador, int resultado) {
		switch (operador) {
		case '+':
			return resultado + x;
		case '-':
			return resultado - x;
		case '*':
			return resultado * x;
		case '/':
			try {
				return resultado / x;
			} catch (ArithmeticException e) {
				System.err.println("ERROR: division por cero");
				display.setText("Math ERROR");
			} catch (Exception e) {
				System.err.println("ERROR");
			}

		case '=':
			return x;
		default:
			return resultado;
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Calculadora();
			}
		});
	}
}
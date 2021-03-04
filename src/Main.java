
import java.util.Arrays;

//6 от 49, кодиране на извадка
class Toto6_49_1 {
	// кодиране в един long (8 бйта) -> всеки бит съответства на число
	// Използват се 49 бита от 64-те е long
	private long data;

	// Конструктор по 6 входни числа
	Toto6_49_1(int a1, int a2, int a3, int a4, int a5, int a6) {
		data = 0; // Остава 0 при некоректни данни
		// Проверка за обхват
		if (a1 < 1 || a1 > 49 || a2 < 1 || a2 > 49 || a3 < 1 || a3 > 49 || a4 < 1 || a4 > 49 || a5 < 1 || a5 > 49
				|| a6 < 1 || a6 > 49)
			return;
		// Проверка за различост
		if (a1 == a2 || a1 == a3 || a1 == a4 || a1 == a5 || a1 == a6 || a2 == a3 || a2 == a4 || a2 == a5 || a2 == a6
				|| a3 == a4 || a3 == a5 || a3 == a6 || a4 == a5 || a4 == a6 || a5 == a6)
			return;
		// Номер на бит за всяко число - с 1 по-малко
		a1--;
		a2--;
		a3--;
		a4--;
		a5--;
		a6--;
		// Създаване на кода:
		data |= (1L << a1);
		data |= (1L << a2);
		data |= (1L << a3);
		data |= (1L << a4);
		data |= (1L << a5);
		data |= (1L << a6);
	}

	@Override
	public String toString() {
		if (data == 0)
			return "Invalid";
		String s = "";
		long d = data;// Копие на кода
		for (int i = 1; d != 0; i++) {
			if ((d & 1) == 1) {// Проверка за най-младшия бит
				if (!s.equals(""))
					s += ", ";
				s += i;
			}
			d >>>= 1; // Преместване надясно на всикчи битове
		}
		return s;
	}

	public long getData() {
		return data;
	}
}

class Toto6_49_2 {
	private byte[] data = { 0, 0, 0 }; // Кодиране в 3 байта
	// ИНСТРУМЕНТИ ЗА ИЗВАДКИ
	// Инициализация на масива (първа извадка по азбучен ред)

	public void init(int k, int[] a) {
		for (int i = 0; i < k; i++)
			a[i] = i + 1;
	}

	// Следваща извадка по азбучен ред
	// Връща false, ако няма следваща
	public boolean nextComb(int n, int k, int[] a) {
		for (int i = a.length - 1; i >= 0; i--)
			if (a[i] < n - k + i + 1) {// Ако не си е достигнала горната граница
				a[i]++; // Увеличаваме с 1
				for (i++; i < a.length; i++)
					a[i] = a[i - 1] + 1;// "Уплътняване" на "опашката"
				return true;
			}
		return false;
	}

	// КОНСТРУКТОР
	public Toto6_49_2(int[] a) {// По масив от елементи
		if (a.length != 6)
			return; // Трябва да има 6 елемента
		Arrays.sort(a); // Сортиране на извадката
		// Проверки за коректност
		if (a[0] < 1)
			return;
		for (int i = 1; i < a.length; i++)
			if (a[i] > 49 || a[i] == a[i - 1])
				return;
		// Генриране на извадки до достигане на желаната
		int[] t = { 0, 0, 0, 0, 0, 0 };
		init(6, t);
		int c = 1;
		do {
			if (Arrays.equals(a, t))
				break;
			c++;
		} while (nextComb(49, 6, t));
		setDataFromCode(c);
	}

	// ГЕТЪРИ
	public int getDataCode() {// Връща кода в int (4B)
		int r = 0;
		for (int i = 2; i >= 0; i--) {
			r <<= 8;
			r |= ((int) data[i] & 0xFF); // Внимание: (int) data[i]
											// ще е отрицателно, ако data[i]<0.
		}
		return r;
	}

	public byte[] getData() {
		return data;
	}

	// СЕТЪРИ
	public void setData(byte[] a) {// От масив
		data = a;
	}

	public void setDataFromCode(int a) {// От int код (пореден номер)
		data[0] = (byte) (a & 0xFF);
		a >>>= 8;
		data[1] = (byte) (a & 0xFF);
		a >>>= 8;
		data[2] = (byte) (a & 0xFF);
	}

	@Override
	public String toString() {
		int code = getDataCode();
		if (code == 0)
			return "Not defined";
		int[] t = { 0, 0, 0, 0, 0, 0 };
		init(6, t);
		int c = 1;
		do {
			if (code == c)
				break;
			c++;
		} while (nextComb(49, 6, t));
		if (code != c)
			return "Not defined";
		String s = Integer.toString(t[0]);
		for (int i = 1; i < 6; i++)
			s += ", " + t[i];
		return s;
	}
}

public class Main {
	public static void main(String[] args) {
		Toto6_49_1 t = new Toto6_49_1(3, 38, 1, 5, 23, 12);
		System.out.println(Long.toBinaryString(t.getData()));
		System.out.println(t);// 1, 3, 5, 12, 23, 38
		int[] d = { 3, 38, 1, 5, 23, 12 };
		Toto6_49_2 tt = new Toto6_49_2(d);
		System.out.println(tt.getDataCode());
		System.out.println(tt);
		tt.setDataFromCode(13983816);// Последна извадка
		System.out.println(tt);
	}
}

package ru.nsu.fit.djachenko.pusher.model.cells;

import ru.nsu.fit.djachenko.pusher.model.Direction;
import ru.nsu.fit.djachenko.pusher.model.Field;

public class Pusher extends Cell//толкатель - это просто управляемый блок. Или нет - вопрос в обработке коллизий
{
	int strength = 1;

    public Pusher(Field field, int x, int y)
	{
		super(Type.PUSHER, field, x, y);
	}

	public void move(Direction dir)
	{
		super.move(dir, strength);
	}
}

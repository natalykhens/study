package ru.nsu.fit.g1201.races.model;

import ru.nsu.fit.g1201.races.ResultController;
import ru.nsu.fit.g1201.races.communication.*;
import ru.nsu.fit.g1201.races.model.roadmaps.MapList;
import ru.nsu.fit.g1201.races.model.roadmaps.RoadMap;

import java.util.Timer;
import java.util.TimerTask;

public class Race
{
	private static MapList mapList = MapList.getInstance();

	private int mapIndex;
	private Road road;
	private Car car;

	private Timer timer;
	private MessageChannel<MessageToView> channel;
	private final ResultController resultController;

	private int scipCount = 4;
	private boolean isAccelerated = false;
	private boolean paused = false;

	private int speed;
	private long points;

	public Race(RaceParameters parameters, MessageChannel<MessageToView> channel, ResultController resultController)
	{
		this.channel = channel;
		this.resultController = resultController;

		this.speed = parameters.getSpeed();
		this.mapIndex = parameters.getMapIndex();
	}

	public void start()
	{
		this.car = new Car(this, 4, 0);
		this.road = new Road(this, MapList.getInstance().getRoadMap(mapIndex, this));

		isAccelerated = false;
		paused = false;
		points = 0;

		road.draw(car);

		send(new RaceStartedMessage(this));

		TimerTask task = new TimerTask()
		{
			private int iterationCount = 0;

			@Override
			public void run()
			{
				iteration(iterationCount++);
			}
		};

		timer = new Timer();
		timer.schedule(task, 0, 100 / speed);
	}

	public boolean ableToMove(int x, int y, Direction direction)
	{
		return road.ableToMove(x, y, direction);
	}

	public void move(int x, int y, Direction direction)
	{
		if (road != null)
		{
			road.move(x, y, direction);
		}
	}

	public void moveCar(Direction direction)
	{
		if (car != null)
		{
			car.move(direction);
		}
	}

	public void accelerate()
	{
		if (!isAccelerated)
		{
			scipCount /= 4;
			isAccelerated = true;
		}
	}

	public void deaccelerate()
	{
		scipCount *= 4;
		isAccelerated = false;
	}

	synchronized void iteration(int index)
	{
		while (paused)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		if (index % scipCount == 0)
		{
			moveCar(Direction.FORWARD);

			if (road != null)
			{
				road.shift();

				road.print();
			}

			points++;
		}
	}

	public void crash()
	{
		stop();
	}

	public void stop()
	{
		resultController.newScores(points, mapIndex);

		channel.set(new RaceStoppedMessage());

		timer.cancel();
		timer = null;
		car = null;
		road = null;
	}

	void send(MessageToView messageToView)
	{
		channel.set(messageToView);
	}

	public synchronized void pause()
	{
		if (!paused)
		{
			paused = true;
			send(new RacePausedMessage(this));
		}
		else
		{
			paused = false;

			notify();
		}
	}

	public Road getRoad()
	{
		return road;
	}

	public Car getCar()
	{
		return car;
	}

	public ResultController getResultController()
	{
		return resultController;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getMapIndex()
	{
		return mapIndex;
	}

	public long getPoints()
	{
		return points;
	}
}

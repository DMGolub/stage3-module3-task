package com.mjc.school.utility;

import com.mjc.school.helper.Command;
import com.mjc.school.helper.CommandSender;
import com.mjc.school.helper.MenuHelper;
import com.mjc.school.helper.Operations;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Conversation {

	private static final String ENTER_COMMAND_MESSAGE = "Please enter command number...";
	private final CommandSender commandSender;
	private final MenuHelper helper;

	public Conversation(final CommandSender commandSender, final MenuHelper helper) {
		this.commandSender = commandSender;
		this.helper = helper;
	}

	public void run() {
		Scanner keyboard = new Scanner(System.in);
		boolean isFinished = false;
		while (!isFinished) {
			try {
				helper.printMainMenu();
				System.out.println(ENTER_COMMAND_MESSAGE);
				int key = (int) helper.getLongWithLowerBound(0L, keyboard);
				if (Operations.EXIT.getOperationNumber() == key) {
					isFinished = true;
					continue;
				}

				Command command = helper.getCommand(String.valueOf(key), keyboard);
				Object result = commandSender.send(command);
				if (result instanceof Iterable it) {
					it.forEach(System.out::println);
				} else {
					System.out.println(result);
				}
			} catch (Exception e) {
				System.out.println(String.format(e.getMessage()));
			}
		}
	}
}
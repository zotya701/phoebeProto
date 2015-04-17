		# phoebeProto
		
		Szoftver labor 4 Proto
		
		A forrásfájlok kódolása Cp1250, máséven Windows-1250.
		
		Könyvtár hierarchia futtatás előtt:
		│   README.md
		│   test.cmd
		│
		├───phoebe_bin
		├───phoebe_src
		│   └───phoebeProto
		│           Cleaner.java
		│           Edge.java
		│           Field.java
		│           GameManager.java
		│           Goo.java
		│           Jumping.java
		│           Landable.java
		│           Map.java
		│           Node.java
		│           NormalField.java
		│           Oil.java
		│           OutsideField.java
		│           Printable.java
		│           Robot.java
		│           RobotState.java
		│           Trap.java
		│
		├───tester_bin
		│   │   test.map
		│   │   test.robots
		│   │
		│   ├───compare_inputs
		│   │       compare_input_Test_1.txt
		│   │       compare_input_Test_10.txt
		│   │       compare_input_Test_11.txt
		│   │       compare_input_Test_12.txt
		│   │       compare_input_Test_13.txt
		│   │       compare_input_Test_2.txt
		│   │       compare_input_Test_3.txt
		│   │       compare_input_Test_4.txt
		│   │       compare_input_Test_5.txt
		│   │       compare_input_Test_6.txt
		│   │       compare_input_Test_7.txt
		│   │       compare_input_Test_8.txt
		│   │       compare_input_Test_9.txt
		│   │
		│   └───test_inputs
		│           test_input_1.txt
		│           test_input_10.txt
		│           test_input_11.txt
		│           test_input_12.txt
		│           test_input_13.txt
		│           test_input_2.txt
		│           test_input_3.txt
		│           test_input_4.txt
		│           test_input_5.txt
		│           test_input_6.txt
		│           test_input_7.txt
		│           test_input_8.txt
		│           test_input_9.txt
		│
		└───tester_src
		        Main.java
				
		Fordítás és futtatás egyszerűen:
			A gyökér könyvtárban shift+jobb klikk, Open command window here, majd futtatjuk a test.cmd-t.
			
		Bonyolultabban:
			A gyökér könyvtárban shift+jobb klikk, Open command windows here, majd futtatjuk az alábbi parancsokat:
				
				Fordítás:		javac -d phoebe_bin phoebe_src/phoebeProto/*.java
								javac -d tester_bin tester_src/*.java
				
				Futtatás:		cd tester_bin
								java tester.Main
		
		
		
		Ha minden jólmegy, ezt kéne látnunk:
		
				"Path to the root directory">test
		
				"Path to the root directory">javac -d phoebe_bin phoebe_src/phoebeProto/*.java
		
				"Path to the root directory">javac -d tester_bin tester_src/*.java
		
				"Path to the root directory">cd tester_bin
		
				"Path to the root directory"\tester_bin>java tester.Main
				Test_1:
						passed
				Test_2:
						passed
				Test_3:
						passed
				Test_4:
						passed
				Test_5:
						passed
				Test_6:
						passed
				Test_7:
						passed
				Test_8:
						passed
				Test_9:
						passed
				Test_10:
						passed
				Test_11:
						passed
				Test_12:
						passed
				Test_13:
						passed
		
				"Path to the root directory"\tester_bin>
				
		Könyvtár hierarchia futtatás után:
		│   README.md
		│   test.cmd
		│
		├───phoebe_bin
		│   └───phoebeProto
		│           Cleaner.class
		│           Edge.class
		│           Field.class
		│           GameManager.class
		│           Goo.class
		│           Jumping.class
		│           Landable.class
		│           Map.class
		│           Node.class
		│           NormalField.class
		│           Oil.class
		│           OutsideField.class
		│           Printable.class
		│           Robot.class
		│           RobotState.class
		│           Trap.class
		│
		├───phoebe_src
		│   └───phoebeProto
		│           Cleaner.java
		│           Edge.java
		│           Field.java
		│           GameManager.java
		│           Goo.java
		│           Jumping.java
		│           Landable.java
		│           Map.java
		│           Node.java
		│           NormalField.java
		│           Oil.java
		│           OutsideField.java
		│           Printable.java
		│           Robot.java
		│           RobotState.java
		│           Trap.java
		│
		├───tester_bin
		│   │   error.txt
		│   │   test.map
		│   │   test.robots
		│   │
		│   ├───compare_inputs
		│   │       compare_input_Test_1.txt
		│   │       compare_input_Test_10.txt
		│   │       compare_input_Test_11.txt
		│   │       compare_input_Test_12.txt
		│   │       compare_input_Test_13.txt
		│   │       compare_input_Test_2.txt
		│   │       compare_input_Test_3.txt
		│   │       compare_input_Test_4.txt
		│   │       compare_input_Test_5.txt
		│   │       compare_input_Test_6.txt
		│   │       compare_input_Test_7.txt
		│   │       compare_input_Test_8.txt
		│   │       compare_input_Test_9.txt
		│   │
		│   ├───compare_outputs
		│   │       compare_output_Test_1.txt
		│   │       compare_output_Test_10.txt
		│   │       compare_output_Test_11.txt
		│   │       compare_output_Test_12.txt
		│   │       compare_output_Test_13.txt
		│   │       compare_output_Test_2.txt
		│   │       compare_output_Test_3.txt
		│   │       compare_output_Test_4.txt
		│   │       compare_output_Test_5.txt
		│   │       compare_output_Test_6.txt
		│   │       compare_output_Test_7.txt
		│   │       compare_output_Test_8.txt
		│   │       compare_output_Test_9.txt
		│   │
		│   ├───tester
		│   │       Main.class
		│   │
		│   ├───test_inputs
		│   │       test_input_1.txt
		│   │       test_input_10.txt
		│   │       test_input_11.txt
		│   │       test_input_12.txt
		│   │       test_input_13.txt
		│   │       test_input_2.txt
		│   │       test_input_3.txt
		│   │       test_input_4.txt
		│   │       test_input_5.txt
		│   │       test_input_6.txt
		│   │       test_input_7.txt
		│   │       test_input_8.txt
		│   │       test_input_9.txt
		│   │
		│   └───test_outputs
		│           test_output_1.txt
		│           test_output_10.txt
		│           test_output_11.txt
		│           test_output_12.txt
		│           test_output_13.txt
		│           test_output_2.txt
		│           test_output_3.txt
		│           test_output_4.txt
		│           test_output_5.txt
		│           test_output_6.txt
		│           test_output_7.txt
		│           test_output_8.txt
		│           test_output_9.txt
		│
		└───tester_src
		        Main.java

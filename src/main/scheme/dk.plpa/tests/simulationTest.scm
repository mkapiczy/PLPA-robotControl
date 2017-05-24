;Test suite requires (include "../robot.scm") in simulation.scm
;Test suite requires (include "../tests/FloorPlanTest2.scm") in FloorUtil
;Test suite requires (include "../FloorUtil.scm") in robot.scm
;https://github.com/akeep/rough-draft
(import  
 (rough-draft unit-test)
 (rough-draft console-test-runner)) 

(include "../simulation.scm")

(define-test-suite simulationTestSuite

  (define-test loadCommands-test
  (let ((testCommands '((0 8 "E") (MoveForward 8) (TurnLeft 1) (MoveForward 7) (TurnLeft 1) (MoveForward 2) (TurnLeft 1) (PickObject "P1")(TurnLeft 1) (MoveForward 2) (TurnRight 1))))
        (loadCommands testCommands)
        (assert-eqv? commands testCommands)
        (assert-eqv? (areCommandsLoaded) #t)
        ))

    
  (define-test moveRobotSetInitialState-test
   (let ((testCommands '((0 8 "E") (MoveForward 8) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 0)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "E")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

   (define-test moveRobotMoveForward-test
   (let ((testCommands '((0 8 "E") (MoveForward 8) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "E")
        (assert-eqv? (send 'getErrorCode robotState) 1)
        )
  )

 (define-test turnLeftOneStep-test
   (let ((testCommands '((8 8 "E") (TurnLeft 1) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "N")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

   (define-test turnLeftMultipleSteps-test
   (let ((testCommands '((8 8 "E") (TurnLeft 3) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "S")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

 (define-test turnRightOneStep-test
   (let ((testCommands '((8 8 "E") (TurnRight 1) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "S")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

   (define-test turnRightMultipleSteps-test
   (let ((testCommands '((8 8 "E") (TurnRight 2) (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "W")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

   (define-test pickObjectOnRightTile-test
   (let ((testCommands '((6 1 "S") (PickObject "P1") (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 6)
        (assert-eqv? (send 'getY robotState) 1)
        (assert-equal? (send 'getDirection robotState) "S")
        (assert-equal? (send 'getCarriedObject robotState) "P1")
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )


(define-test pickObjectOnWrongTile-test
   (let ((testCommands '((6 2 "S") (PickObject "P1") (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 6)
        (assert-eqv? (send 'getY robotState) 2)
        (assert-equal? (send 'getDirection robotState) "S")
        (assert-equal? (send 'getCarriedObject robotState) '())
        (assert-eqv? (send 'getErrorCode robotState) -1)
        )
  )

(define-test dropObjectOnRightTile-test
   (let ((testCommands '((8 4 "W") (DropObject "P1") (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (set! robotState (robot 8 4 "W" 0 "P1"))
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 4)
        (assert-equal? (send 'getDirection robotState) "W")
        (assert-equal? (send 'getCarriedObject robotState) '())
        (assert-eqv? (send 'getErrorCode robotState) 0)
        )
  )

  (define-test dropObjectOnWrongTile-test
   (let ((testCommands '((8 8 "W") (DropObject "P1") (TurnOffRobot 1))))
        (loadCommands testCommands)
        (moveRobot)
        (moveRobot)
        (set! robotState (robot 8 8 "W" 0 "P1"))
        (moveRobot)
        (assert-eqv? (send 'getX robotState) 8)
        (assert-eqv? (send 'getY robotState) 8)
        (assert-equal? (send 'getDirection robotState) "W")
        (assert-equal? (send 'getCarriedObject robotState) "P1")
        (assert-eqv? (send 'getErrorCode robotState) -1)
        )
  )







  )

 
 

(run-test-suite simulationTestSuite)
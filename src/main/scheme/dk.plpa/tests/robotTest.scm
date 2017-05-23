;Test suite requires (include "../tests/FloorPlanTest2.scm") in FloorUtil.scm
;Test suite requires (include "../FloorUtil.scm") in robot.scm
;https://github.com/akeep/rough-draft
(import  
 (rough-draft unit-test)
 (rough-draft console-test-runner)) 

(include "../robot.scm")

(define-test-suite robotTestSuite
  
  (define-test robotMoveForward-test
    (let ((robot (robot 0 8 "E" 0 '())))
      (let ((newRobot (send 'moveForward robot 8)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnLeftOneStep-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnLeft robot 1)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "N")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnLeftTwoSteps-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnLeft robot 2)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "W")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnLeftThreeSteps-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnLeft robot 3)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "S")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))


  (define-test robotTurnRightOneStep-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnRight robot 1)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "S")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnRightTwoSteps-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnRight robot 2)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "W")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnRightThreeSteps-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnRight robot 3)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "N")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotTurnOff-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'turnOff robot)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "E")
        (assert-eqv? (send 'getErrorCode newRobot) 1)
        )
      ))

  (define-test robotPickObjectOnWrongTile-test
    (let ((robot (robot 8 8 "E" 0 '())))
      (let ((newRobot (send 'pickObject robot "P1")))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "E")
        (assert-equal? (send 'getCarriedObject newRobot) '())
        (assert-eqv? (send 'getErrorCode newRobot) -1)
        )
      ))

  (define-test robotPickObjectOnRightTile-test
    (let ((robot (robot 6 1 "S" 0 '())))
      (let ((newRobot (send 'pickObject robot "P1")))
        (assert-eqv? (send 'getX newRobot) 6)
        (assert-eqv? (send 'getY newRobot) 1)
        (assert-equal? (send 'getDirection newRobot) "S")
        (assert-equal? (send 'getCarriedObject newRobot) "P1")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotPickObjectOnRightStarTile-test
    (let ((robot (robot 29 2 "N" 0 '())))
      (let ((newRobot (send 'pickObject robot "P1")))
        (assert-eqv? (send 'getX newRobot) 29)
        (assert-eqv? (send 'getY newRobot) 2)
        (assert-equal? (send 'getDirection newRobot) "N")
        (assert-equal? (send 'getCarriedObject newRobot) "P1")
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotPickObjectWhenAlreadyCarriesOne-test
    (let ((robot (robot 6 1 "S" 0 "P1")))
      (let ((newRobot (send 'pickObject robot "P2")))
        (assert-eqv? (send 'getX newRobot) 6)
        (assert-eqv? (send 'getY newRobot) 1)
        (assert-equal? (send 'getDirection newRobot) "S")
        (assert-equal? (send 'getCarriedObject newRobot) "P1")
        (assert-eqv? (send 'getErrorCode newRobot) -1)
        )
      ))

(define-test robotDropObjectOnWrongTile-test
    (let ((robot (robot 8 8 "E" 0 "P1")))
      (let ((newRobot (send 'dropObject robot)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-equal? (send 'getDirection newRobot) "E")
        (assert-equal? (send 'getCarriedObject newRobot) "P1")
        (assert-eqv? (send 'getErrorCode newRobot) -1)
        )
      ))

    
(define-test robotDropObjectOnRightTile-test
    (let ((robot (robot 8 4 "W" 0 "P1")))
      (let ((newRobot (send 'dropObject robot)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 4)
        (assert-equal? (send 'getDirection newRobot) "W")
        (assert-equal? (send 'getCarriedObject newRobot) '())
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

  (define-test robotDropObjectOnRightStarTile-test
    (let ((robot (robot 29 2 "N" 0 "P1")))
      (let ((newRobot (send 'dropObject robot)))
        (assert-eqv? (send 'getX newRobot) 29)
        (assert-eqv? (send 'getY newRobot) 2)
        (assert-equal? (send 'getDirection newRobot) "N")
        (assert-equal? (send 'getCarriedObject newRobot) '())
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))

(define-test robotDropObjectOnRightTileWithNoCarriedObject-test
    (let ((robot (robot 8 4 "W" 0 '())))
      (let ((newRobot (send 'dropObject robot)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 4)
        (assert-equal? (send 'getDirection newRobot) "W")
        (assert-equal? (send 'getCarriedObject newRobot) '())
        (assert-eqv? (send 'getErrorCode newRobot) -1)
        )
      ))

  )

 
 

(run-test-suite robotTestSuite)
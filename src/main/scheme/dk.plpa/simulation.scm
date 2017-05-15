;(include "robot.scm")

;(define commands '((0 8 "E") (MoveForward 8) (TurnLeft 1) (MoveForward 7) (TurnLeft 1) (MoveForward 2) (TurnLeft 1) (PickObject "P1")(TurnLeft 1) (MoveForward 2) (TurnRight 1) (MoveForward 7) (DropObject 1)))

(define commands '())

(define commandPointer 0)

(define (loadCommands values)
    (set! commands values))

(define (resetCommandPointer)
    (set! commandPointer 0))

(define robotState (robot 0 0 "E" 0 '()))

(define getNextRobotState
      (lambda ()
              (lambda (proc steps)
              (if (= commandPointer 0)
                (set! robotState (robot (list-ref (list-ref commands commandPointer)0) (list-ref (list-ref commands commandPointer)1) (list-ref (list-ref commands commandPointer)2) 0 '()))
                (set! robotState (proc robotState steps))
              )
            (set! commandPointer (+ commandPointer 1))
            (if (or (= (send 'getErrorCode robotState) 1) (= (send 'getErrorCode robotState) -1))
                (resetCommandPointer)
            )
            (send 'getRobotStateAsList robotState))))

(define (MoveForward state noOfSteps)
    (send 'moveForward state noOfSteps)
)

(define (TurnRight state noOfSteps)
    (send 'turnRight state noOfSteps)
)

(define (TurnLeft state noOfSteps)
    (send 'turnLeft state noOfSteps)
)

(define (PickObject state object)
    (send 'pickObject state object)
)

(define (DropObject state object)
    (send 'dropObject state)
)

(define (GetX state)
    (send 'getX robotState)
)

(define (TurnOffRobot state controlCode)
    (send 'turnOff robotState)
)

(define step (getNextRobotState))

(define (moveRobot)
    (let ((command (list-ref (list-ref commands commandPointer)0)))
    (let ((steps (list-ref (list-ref commands commandPointer)1)))
        (step (eval command) steps)
    )))

(define (printGlobalValues)
    (for-each (lambda (commands)
    (display commands)
    (newline))
    commands))

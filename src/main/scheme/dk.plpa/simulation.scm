;;Robot simulation
(include "robot.scm")

(define commands '())

(define (loadCommands values)
    (set! commands values))

(define (printGlobalValues)
    (for-each (lambda (commands)
    (display commands)
    (newline))
    commands))

(define robotState
      (lambda (init)
          (let ((state init))
              (lambda (proc steps)
                  (set! state (proc state steps))
                    state))))

(define (MoveForward state noOfSteps)
    (send 'moveForward state noOfSteps)
)

(define r (robot 8 5 "S" 0 '()))

(define step (robotState r))

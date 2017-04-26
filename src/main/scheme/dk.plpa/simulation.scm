;;Robot simulation
(define commands '())

(define (loadCommands values)
    (set! commands values))

(define (printGlobalValues)
    (for-each (lambda (commands)
    (display commands)
    (newline))
    commands))

 #|
(define robotState
      (lambda (init)
          (let ((state init))
              (lambda (proc)
                  (set! state (proc))
                    state))))

(define (MoveForward noOfSteps)
    (send 'moveForward (robotState) noOfSteps)
)

(define r (robot 8 5 "S" 0 '()))

(define step (robotState r))

(step (MoveForward 2))
|#

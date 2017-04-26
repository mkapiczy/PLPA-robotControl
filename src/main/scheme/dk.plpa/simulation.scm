;;Robot simulation

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

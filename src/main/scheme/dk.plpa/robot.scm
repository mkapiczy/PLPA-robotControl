(include "FloorUtil.scm")

(define (robot x y direction errorCode)
  (letrec (
           (getX    (lambda () x))
           (getY    (lambda () y))
           (getDirection (lambda () direction))
           (getErrorCode (lambda () errorCode))
           (moveForward (lambda (amountOfSteps)
                          (if (> amountOfSteps 0)
                             (send 'moveForward (moveForwardOneStep) (- amountOfSteps 1))
                             (robot
                                 x
                                 y
                                 direction
                                 errorCode)
                          )))
          (moveForwardOneStep (lambda()
          (if (isMovementAllowed "FORWARD")
              (begin
                (display (string-append "Robot moved forward from " (number->string x) " " (number->string y) " to " (number->string (getNextXPosition "FORWARD")) " "  (number->string (getNextYPosition "FORWARD"))))
                (robot
                 (getNextXPosition "FORWARD")
                 (getNextYPosition "FORWARD")
                 direction
                 errorCode))
              (begin
                (display (string-append "Movement forward from " (number->string x) " " (number->string y) " not allowed"))
                (robot
                 x
                 y
                 direction
                 -1)))
          ))
           (turnRight (lambda ()
                        (display (string-append "Robot turned right from " direction " to " (getNextDirection "RIGHT")))
                        (robot
                         x
                         y
                         (getNextDirection "RIGHT")
                         errorCode)))
           (turnLeft (lambda ()
                       (display (string-append "Robot turned left from " direction " to " (getNextDirection "LEFT")))
                       (robot
                        x
                        y
                        (getNextDirection "LEFT")
                        errorCode)))

           (type-of (lambda () 'robot))


           (getNextXPosition (lambda (movementDirection)
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) x)
                                     ((equal? "E" direction) (+ x 1))
                                     ((equal? "S" direction) x)
                                     ((equal? "W" direction) (- x 1))
                                     )
                                   x
                                   )))
           (getNextYPosition (lambda (movementDirection)
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) (- y 1))
                                     ((equal? "E" direction) y)
                                     ((equal? "S" direction) (+ y 1))
                                     ((equal? "W" direction) y)
                                     )
                                   y
                                   )))

           (getNextDirection (lambda (rotationDirection)
                               (if (equal? rotationDirection "RIGHT")
                                   (cond
                                     ((equal? "N" direction) "E")
                                     ((equal? "E" direction) "S")
                                     ((equal? "S" direction) "W")
                                     ((equal? "W" direction) "N"))
                                   (cond
                                     ((equal? "N" direction) "W")
                                     ((equal? "W" direction) "S")
                                     ((equal? "S" direction) "E")
                                     ((equal? "E" direction) "N"))
                                   )
                               ))

           (isMovementAllowed (lambda (movementDirection)
                                (cond
                                  ((equal? 'A (get-tile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                                  ((equal? 'P (get-tile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                                  ((equal? 'o (get-tile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                                  ((equal? '* (get-tile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                                  ((equal? 'i (get-tile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                                  (else #f)
                                  )
                                ))
           )


    (lambda (message)
      (cond ((eq? message 'getX) getX)
            ((eq? message 'getY) getY)
            ((eq? message 'getDirection)  getDirection)
            ((eq? message 'moveForward)  moveForward)
            ((eq? message 'turnRight)  turnRight)
            ((eq? message 'turnLeft)  turnLeft)
            ((eq? message 'getErrorCode)  getErrorCode)
            ((eq? message 'type-of) type-of)
            (else (error "Message not understood"))))
    )
  )




(define (send message object . args)
  (let ((method (object message)))
    (cond ((procedure? method) (apply method args))
          (else (error "Error in method lookup " method)))))

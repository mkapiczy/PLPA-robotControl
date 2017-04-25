(include "FloorUtil.scm")

(define (robot x y direction errorCode)
  (letrec (
           (getX    (lambda () x))
           (getY    (lambda () y))
           (getDirection (lambda () direction))
           (getErrorCode (lambda () errorCode))
           (moveForward (lambda (amountOfSteps)
                          (if (isMovementAllowed "FORWARD" amountOfSteps)
                              (begin
                                (display (string-append "Robot moved forward from " (number->string x) " " (number->string y) " to " (number->string (getNextXPosition "FORWARD" amountOfSteps)) " "  (number->string (getNextYPosition "FORWARD" amountOfSteps))))
                                (robot
                                 (getNextXPosition "FORWARD" amountOfSteps)
                                 (getNextYPosition "FORWARD" amountOfSteps)
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


           (getNextXPosition (lambda (movementDirection amountOfSteps)
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) x)
                                     ((equal? "E" direction) (+ x amountOfSteps))
                                     ((equal? "S" direction) x)
                                     ((equal? "W" direction) (- x amountOfSteps))
                                     )
                                   x
                                   )))
           (getNextYPosition (lambda (movementDirection amountOfSteps)
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) (- y amountOfSteps))
                                     ((equal? "E" direction) y)
                                     ((equal? "S" direction) (+ y amountOfSteps))
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

           (isMovementAllowed (lambda (movementDirection amountOfSteps)
                                (cond
                                  ((equal? 'A (get-tile (getNextXPosition movementDirection amountOfSteps) (getNextYPosition movementDirection amountOfSteps))) #t)
                                  ((equal? 'P (get-tile (getNextXPosition movementDirection amountOfSteps) (getNextYPosition movementDirection amountOfSteps))) #t)
                                  ((equal? 'o (get-tile (getNextXPosition movementDirection amountOfSteps) (getNextYPosition movementDirection amountOfSteps))) #t)
                                  ((equal? '* (get-tile (getNextXPosition movementDirection amountOfSteps) (getNextYPosition movementDirection amountOfSteps))) #t)
                                  ((equal? 'i (get-tile (getNextXPosition movementDirection amountOfSteps) (getNextYPosition movementDirection amountOfSteps))) #t)
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

(define factorial (lambda (x)
                (if (= x 1)
                    x
                    (* (factorial (- x 1)) x))))

(define (increment i)
    (+ i 1))

(define (incrementAtOnce i)
    (incrementAtOnceTail i '()))

(define (incrementAtOnceTail i r)
    (if (< i 10)
       (incrementAtOnceTail (+ i 1) (append r (list (+ i 1))))
        r))

(define x '())

(define (loadValues values)
    (set! x values))

(define (printGlobalValues)
    (for-each (lambda (x)
    (display x)
    (newline))
    x))

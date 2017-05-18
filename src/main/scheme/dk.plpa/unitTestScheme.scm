(import
  (rough-draft unit-test)
  (rough-draft console-test-runner))

(define-test-suite foo
  (define-test first-test
    (assert-eqv? 10 10)
  ))
 

(run-test-suite foo)

;(run-test foo first-test)


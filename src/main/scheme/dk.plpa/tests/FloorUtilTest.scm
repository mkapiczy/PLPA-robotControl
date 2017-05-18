(import
  (rough-draft unit-test)
  (rough-draft console-test-runner))

(include "../FloorUtil.scm")


(define-test-suite foo
  (define-test getFactoryFloor-test
    (assert-eqv? factoryFloor (getFactoryFloor))
  ))
 

(run-test-suite foo)

;(run-test foo getFactoryFloor-test)


# elbow
Use Replumb in Node

# Prereq's

Need a fix for https://github.com/ScalaConsultants/replumb/issues/20
Can use https://github.com/mfikes/replumb/tree/node-compat

# Usage

1. `lein cljsbuild once`
2. `node out/main.js <src-paths>`

where `src-paths` looks like `src1:/bar/src2:/foo/src3`

# Example

```
$ node out/main.js
cljs.user=> 3
3
cljs.user=> (+ 3 2)
5
cljs.user=> (ns foo.bar)
nil
foo.bar=> ::a
:foo.bar/a
```
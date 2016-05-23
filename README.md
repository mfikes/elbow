# elbow
Use Replumb in Node

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

# Demo

Watch a demo to see it in action:

[![Elbow Demo](http://img.youtube.com/vi/VwARsqTRw7s/0.jpg)](http://www.youtube.com/watch?v=VwARsqTRw7s "Replumb in Node")

# License

Copyright © 2015–2016 Mike Fikes and Contributors

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.

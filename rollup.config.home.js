import resolve from 'rollup-plugin-node-resolve';

export default {
  entry: 'src/javascript/home.js',
  dest: 'resources/public/assets/home.min.js',
  format: 'iife',
  sourceMap: 'inline',
  plugins: [
    resolve({
      jsnext: true
    })
  ]
};

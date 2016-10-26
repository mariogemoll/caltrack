import resolve from 'rollup-plugin-node-resolve';

export default {
  entry: 'src/javascript/report.js',
  dest: 'resources/public/assets/report.min.js',
  format: 'iife',
  sourceMap: 'inline',
  plugins: [
    resolve({
      jsnext: true
    })
  ]
};

// using webpack 4
// see https://itnext.io/how-to-optimise-your-serverless-typescript-webpack-eslint-setup-for-performance-86d052284505

// runs TypeScript linting on separate process
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

module.exports = {
  context: __dirname,
  devtool: 'inline-source-map',
  entry: './src/main.ts',
  resolve: {
    extensions: ['.mjs', '.json', '.ts'],
    symlinks: true,
    cacheWithContext: false
  },
  output: {
    filename: 'index.bundle.js',
    assetModuleFilename: 'images/[hash][ext][query]'
  },
  target: 'web',
  devServer: {
    hot: true,
    open: true,
    port: 3000
  },
  module: {
    rules: [
      {
        // all files with a '.json' extension will be handled by the json-loader
        test: /\.json$/,
        loader: 'json5-loader'
      },
      {
        // all files with a `.ts` or `.tsx` extension will be handled by `ts-loader`
        test: /\.(tsx?)$/,
        loader: 'ts-loader',
        exclude: [[/node_modules/], [/.serverless/], [/.webpack/]],
        options: {
          transpileOnly: true,
          experimentalWatchApi: true
        }
      }
    ]
  },
  plugins:  [
    new ForkTsCheckerWebpackPlugin({
      eslint: {
       files: '{ts,tsx,js,jsx}'
     },
     typescript: true
   })
  ]
};

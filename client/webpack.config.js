const HtmlWebpackPlugin = require('html-webpack-plugin');
// hot reload CSS:
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');

module.exports = {
  entry: './src/index.tsx',
  output: {
    path: path.join(__dirname, 'build'),
    filename: 'index.bundle.js',
    assetModuleFilename: 'images/[hash][ext][query]'
  },
  target: 'web',
  devServer: {
    historyApiFallback: true,
    hot: true,
    open: true,
    port: 3000
  },
  performance: {
    hints: false
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.json']
  },
  module: {
    rules: [
      {
        test: /\.(scss|css)$/,
        use: ['style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.tsx?$/,
        use: 'ts-loader'
      }
    ]
  },
  plugins: [new MiniCssExtractPlugin(), new HtmlWebpackPlugin({ template: './src/index.html' })]
};

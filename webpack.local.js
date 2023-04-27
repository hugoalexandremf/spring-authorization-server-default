const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');
const optimizationOptions = {
  minimize: true,
  minimizer: [
    new TerserPlugin({
      terserOptions: {
        format: {
          comments: false,
        },
      },
      extractComments: false,
    }),
  ],
};

module.exports = [
  {
    mode: 'development',
    entry: ['./src/main/resources/templates/JS/HomepageEntry.js'],
    devtool: 'eval-source-map',
    cache: true,
    output: {
      path: path.resolve(__dirname, './target/classes/static/bundles/'),
      filename: 'homepage.js',
    },
    resolve: {
      extensions: ['.js', '.jsx'],
    },
    module: {
      rules: [
        {
          test: /\.(js|jsx)$/,
          exclude: /(node_modules)/,
          loader: 'babel-loader',
          options: {
            cacheDirectory: true,
            presets: [
              [
                '@babel/preset-env',
                {
                  useBuiltIns: 'usage',
                  corejs: {
                    version: '3.21.1',
                  },
                },
              ],
              ['@babel/react'],
            ],
          },
        },
        /*{
                    test: /\.css$/,
                    use: ["style-loader", "css-loader"]
                },*/
        {
          test: /\.css$/i,
          use: ['style-loader', 'css-loader', 'postcss-loader'],
        },

        {
          test: /\.(png|jpg|gif)$/,
          type: 'asset/inline',
        },
      ],
    },
    //optimization: optimizationOptions,
  },
  {
    mode: 'development',
    entry: ['./src/main/resources/templates/JS/LoginEntry.js'],
    devtool: 'eval-source-map',
    cache: true,
    output: {
      path: path.resolve(__dirname, './target/classes/static/bundles/'),
      filename: 'login.js',
    },
    resolve: {
      extensions: ['.js', '.jsx'],
    },
    module: {
      rules: [
        {
          test: /\.(js|jsx)$/,
          exclude: /(node_modules)/,
          loader: 'babel-loader',
          options: {
            cacheDirectory: true,
            presets: [
              [
                '@babel/preset-env',
                {
                  useBuiltIns: 'usage',
                  corejs: {
                    version: '3.21.1',
                  },
                },
              ],
              ['@babel/react'],
            ],
          },
        },
        /*{
                    test: /\.css$/,
                    use: ["style-loader", "css-loader"]
                },*/
        {
          test: /\.css$/i,
          use: ['style-loader', 'css-loader', 'postcss-loader'],
        },

        {
          test: /\.(png|jpg|gif)$/,
          type: 'asset/inline',
        },
      ],
    },
    //optimization: optimizationOptions,
  },
];

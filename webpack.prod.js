const path = require('path');
const TerserPlugin = require("terser-webpack-plugin");
const optimizationOptions = {
    minimize: true,
    minimizer: [
        new TerserPlugin({
            terserOptions: {
                format: {
                    comments: false
                }
            },
            extractComments: false
        })
    ]
};

module.exports = [
    {
        mode: 'production',
        entry: ['./src/main/resources/templates/JS/SpringReactLaboratoryEntry.js'],
        output: {
            path: path.resolve(__dirname, "./target/classes/static/bundles/"),
            filename: 'springReactLaboratory.js'
        },
        resolve: {
            extensions: ['.js', '.jsx']
        },
        module: {
            rules: [
                {
                    test: /\.(js|jsx)$/,
                    exclude: /(node_modules)/,
                    use: {
                        loader: "babel-loader",
                        options: {
                            presets: [
                                ["@babel/preset-env", {
                                    useBuiltIns: "usage",
                                    corejs: {
                                        version: "3.21.1"
                                    }
                                }],
                                ["@babel/react"]
                            ]
                        }
                    }
                },
                {
                    test: /\.css$/,
                    use: ["style-loader", "css-loader"]
                },
                {
                    test: /\.(png|jpg|gif)$/,
                    type: "asset/inline"
                }
            ]
        },
        optimization: optimizationOptions
    }
];
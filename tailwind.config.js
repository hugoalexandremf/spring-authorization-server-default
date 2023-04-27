/** @type {import('tailwindcss').Config} */

/*module.exports = {
  content: [],
  theme: {
    extend: {},
  },
  plugins: [],
}
*/

module.exports = {
  content: ['./src/**/*.{html,js,jsx}'],
  theme: {
    extend: {
      colors: {
        primary: '#1B73E8',
      },
    },
  },
  plugins: [
    // ...
    require('@tailwindcss/forms'),
  ],
};

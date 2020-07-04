export const regExpList = [
  {
    name: 'REGEX_ID_CARD',
    title: '身份证号码',
    type: 'custom',
    pattern: '(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])'
  },
  {
    name: 'REGEX_IP_ADDR',
    title: 'IP地址',
    type: 'custom',
    pattern:
      '(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})'
  },
  {
    name: 'REGEX_MOBILE',
    title: '手机号码',
    type: 'custom',
    pattern:
      '((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))'
  },
  {
    name: 'EMAIL',
    title: 'Email',
    type: 'built-in',
    pattern: ''
  }
];
